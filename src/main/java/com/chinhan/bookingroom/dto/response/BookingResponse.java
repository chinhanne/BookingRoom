package com.chinhan.bookingroom.dto.response;

import com.chinhan.bookingroom.enums.BookingStatus;
import com.chinhan.bookingroom.enums.PaymentMethod;
import com.chinhan.bookingroom.enums.PaymentStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingResponse {
    String id;
    String roomId;
    String userId;
    LocalDateTime checkIn;
    LocalDateTime checkOut;
    BigDecimal totalPrice;
    PaymentMethod paymentMethod;
    PaymentStatus paymentStatus;
    BookingStatus bookingStatus;
}
