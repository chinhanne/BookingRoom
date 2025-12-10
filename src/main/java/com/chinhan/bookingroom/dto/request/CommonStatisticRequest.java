package com.chinhan.bookingroom.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommonStatistic {
    int totalBooking = 0;
    int totalBookingConfirm = 0;
    int totalBookingCancel = 0;
    BigDecimal totalRevenue = BigDecimal.ZERO;
}