package com.chinhan.bookingroom.service;

import com.chinhan.bookingroom.dto.request.BookingRequest;
import com.chinhan.bookingroom.dto.request.LockRequest;
import com.chinhan.bookingroom.dto.response.BookingResponse;
import com.chinhan.bookingroom.dto.response.LockResponse;
import com.chinhan.bookingroom.entity.Booking;
import com.chinhan.bookingroom.entity.RedisRoomLock;
import com.chinhan.bookingroom.entity.Room;
import com.chinhan.bookingroom.entity.User;
import com.chinhan.bookingroom.enums.BookingStatus;
import com.chinhan.bookingroom.enums.PaymentMethod;
import com.chinhan.bookingroom.enums.PaymentStatus;
import com.chinhan.bookingroom.exception.AppException;
import com.chinhan.bookingroom.exception.ErrorCode;
import com.chinhan.bookingroom.mapper.BookingMapper;
import com.chinhan.bookingroom.repository.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookingService {
    private static final Logger log = LoggerFactory.getLogger(BookingService.class);
    BookingRepository bookingRepository;
    LockRoomRepository lockRoomRepository;
    RoomRepository roomRepository;
    BookingMapper bookingMapper;
    UserRepository userRepository;

    long LOCK_TTL = 600; // 10p

    public String getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);

        }

        return authentication.getName(); // id user
    }

    public boolean checkAvailability(String roomId, LocalDateTime checkIn, LocalDateTime checkOut){
        try {
            Room room = roomRepository.findById(roomId)
                    .orElseThrow(() -> new AppException(ErrorCode.ROOM_NOT_EXISTED));

            List<Booking> bookings = bookingRepository.findByRoomAndCheckOutAfterAndCheckInBefore(room, checkIn, checkOut);
            if (!bookings.isEmpty()) return false;

            List<RedisRoomLock> locks = lockRoomRepository.findByRoomId(roomId);

            boolean isAvailable = locks.stream().noneMatch(lock -> !(checkOut.isBefore(lock.getCheckIn()) ||
                    checkIn.isAfter(lock.getCheckOut())));

            return isAvailable; // true neu khong co lock nao trung / false phong dang lock
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public LockResponse createLock(LockRequest request, String userId){
        var lock = bookingMapper.toLock(request);
        lock.setUserId(userId);
        lock.setTtl(LOCK_TTL);

        if(!checkAvailability(lock.getRoomId(), lock.getCheckIn(), lock.getCheckOut()))
            throw new AppException(ErrorCode.ROOM_NOT_AVALIABLE);

        return bookingMapper.toLockResponse(lockRoomRepository.save(lock));
    }


    @Transactional
    public BookingResponse booking(BookingRequest request){
        String currentUserId = getCurrentUserId();


        RedisRoomLock lock = lockRoomRepository.findById(request.getLockId()).orElseThrow(() ->
                new AppException(ErrorCode.LOCK_NOT_EXISTED));

        if(!lock.getUserId().equals(currentUserId))
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        if (lock.getTtl() <= 0) {
            throw new AppException(ErrorCode.LOCK_EXPIRED);
        }

        Room room = roomRepository.findById(lock.getRoomId()).orElseThrow(() ->
                new AppException(ErrorCode.ROOM_NOT_EXISTED));

        User user = userRepository.findById(currentUserId).orElseThrow(() ->
                new AppException(ErrorCode.USER_NOT_EXISTED));

        long days = ChronoUnit.DAYS.between(lock.getCheckIn().toLocalDate(),lock.getCheckOut().toLocalDate());
        if(room.getPrice() == null){
            throw new AppException(ErrorCode.PRICE_NOT_NULL);
        }
        BigDecimal totalPrice = room.getPrice().multiply(BigDecimal.valueOf(days));


        Booking booking = Booking.builder()
                .room(room)
                .user(user)
                .checkIn(lock.getCheckIn())
                .checkOut(lock.getCheckOut())
                .totalPrice(totalPrice)
                .paymentMethod(request.getPaymentMethod())
                .paymentStatus(request.getPaymentMethod() == PaymentMethod.CASH ? PaymentStatus.UNPAID : PaymentStatus.PENDING)
                .status(BookingStatus.CONFIRMED)
                .build();
        bookingRepository.saveAndFlush(booking);

        deleteLock(lock.getLockId());
        return bookingMapper.toBookingResponse(booking);

    }

    @Transactional
    public void deleteLock(String lockId) {
        lockRoomRepository.deleteById(lockId);
    }


    public void cancleBooking(String lockId){
        lockRoomRepository.deleteById(lockId);
    }
}
