package com.chinhan.bookingroom.entity;

import com.chinhan.bookingroom.enums.BookingStatus;
import com.chinhan.bookingroom.enums.PaymentMethod;
import com.chinhan.bookingroom.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Booking {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false)
    String id;

    @ManyToOne
    @JoinColumn(name = "room_id")
    Room room;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    LocalDateTime checkIn;
    LocalDateTime checkOut;
    BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    BookingStatus status;

    @Enumerated(EnumType.STRING)
    PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    PaymentStatus paymentStatus;
}
