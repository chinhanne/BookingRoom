package com.chinhan.bookingroom.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LockRequest {
    String roomId;
    LocalDateTime checkIn;
    LocalDateTime checkOut;
}
