package com.chinhan.bookingroom.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LockResponse {
    String lockId;
    String roomId;
    String userId;
    LocalDateTime checkIn;
    LocalDateTime checkOut;
    long ttl;
}
