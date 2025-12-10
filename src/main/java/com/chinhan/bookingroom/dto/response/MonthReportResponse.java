package com.chinhan.bookingroom.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DailyReportResponse {
    LocalDate date;
    Long totalBooking;
    Long numberBookingConfirm;
    Long numberBookingCancle;
    BigDecimal totalRevenue;
}
