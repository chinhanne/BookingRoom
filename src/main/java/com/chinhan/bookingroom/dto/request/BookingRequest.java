package com.chinhan.bookingroom.dto.request;

import com.chinhan.bookingroom.enums.PaymentMethod;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingRequest {
    String lockId;
    PaymentMethod paymentMethod;
}
