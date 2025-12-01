package com.chinhan.bookingroom.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AmenityResponse {
    String id;
    String name;
    String description;
    boolean statusDelete;
    LocalDateTime createAt;
    LocalDateTime updateAt;
}
