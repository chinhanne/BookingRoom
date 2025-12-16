package com.chinhan.bookingroom.dto.request;

import com.chinhan.bookingroom.entity.Amenity;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoomSearchRequest {

    @NotNull
    LocalDateTime checkIn;

    @NotNull
    LocalDateTime checkOut;

    BigDecimal minPrice;
    BigDecimal maxPrice;
    int capacity;
    String roomType;
    List<Amenity> amenities;
}
