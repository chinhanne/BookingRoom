package com.chinhan.bookingroom.entity;

import org.springframework.data.annotation.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@RedisHash("room_lock")
public class RedisRoomLock {
    @Id
    String lockId;

    String roomId;
    String userId;
    LocalDateTime checkIn;
    LocalDateTime checkOut;
    @TimeToLive
    long ttl;
}
