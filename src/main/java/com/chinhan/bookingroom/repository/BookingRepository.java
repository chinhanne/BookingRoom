package com.chinhan.bookingroom.repository;

import com.chinhan.bookingroom.entity.Booking;
import com.chinhan.bookingroom.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, String> {
    List<Booking> findByRoomAndCheckOutAfterAndCheckInBefore(
            Room room, LocalDateTime checkIn, LocalDateTime checkOut
    );
}
