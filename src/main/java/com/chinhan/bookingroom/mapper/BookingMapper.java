package com.chinhan.bookingroom.mapper;

import com.chinhan.bookingroom.dto.request.BookingRequest;
import com.chinhan.bookingroom.dto.request.LockRequest;
import com.chinhan.bookingroom.dto.response.BookingResponse;
import com.chinhan.bookingroom.dto.response.LockResponse;
import com.chinhan.bookingroom.entity.Booking;
import com.chinhan.bookingroom.entity.RedisRoomLock;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",  nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BookingMapper {
    Booking toBooking(BookingRequest request);
    @Mapping(source = "room.id", target = "roomId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "status", target = "bookingStatus")
    BookingResponse toBookingResponse(Booking booking);

    RedisRoomLock toLock(LockRequest request);
    LockResponse toLockResponse(RedisRoomLock lock);

}
