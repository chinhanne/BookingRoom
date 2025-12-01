package com.chinhan.bookingroom.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoomResponse {
    String id;
    String code;
    int floor;
    int capacity;
    String description;
    BigDecimal price;
    String roomTypeName;
    List<String> amenities;
    List<String> images;
    boolean statusDelete;
    LocalDateTime createAt;
    LocalDateTime updateAt;
}
