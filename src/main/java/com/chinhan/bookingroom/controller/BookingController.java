package com.chinhan.bookingroom.controller;

import com.chinhan.bookingroom.dto.ApiResponse;
import com.chinhan.bookingroom.dto.request.BookingRequest;
import com.chinhan.bookingroom.dto.request.LockRequest;
import com.chinhan.bookingroom.dto.response.BookingResponse;
import com.chinhan.bookingroom.dto.response.LockResponse;
import com.chinhan.bookingroom.service.BookingService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/booking_room")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookingController {
    BookingService bookingService;

    @GetMapping("/availability/{roomId}")
    public ApiResponse<Boolean> checkAvailability(@PathVariable String roomId,
                                                  @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime checkIn,
                                                  @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime checkOut)                                        {
        return ApiResponse.<Boolean>builder()
                .result(bookingService.checkAvailability(roomId,checkIn,checkOut))
                .build();
    }

    @PostMapping("/lock_room")
    public ApiResponse<LockResponse> lockRoom(@RequestBody LockRequest request){
        String currentUserId = bookingService.getCurrentUserId();
        return ApiResponse.<LockResponse>builder()
                .result(bookingService.createLock(request, currentUserId))
                .build();
    }

    @PostMapping("/confirm_booking")
    public ApiResponse<BookingResponse> confirmBooking(@RequestBody BookingRequest request){
        return ApiResponse.<BookingResponse>builder()
                .result(bookingService.booking(request))
                .build();
    }

    @DeleteMapping("/lock/{lockId}")
    public ApiResponse<Void> deleteLock(@PathVariable String id){
        bookingService.cancleBooking(id);
        return ApiResponse.<Void>builder().build();
    }
}
