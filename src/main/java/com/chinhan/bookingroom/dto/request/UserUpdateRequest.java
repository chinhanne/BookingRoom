package com.chinhan.bookingroom.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    String username;
    String password;
    String firstname;
    String lastname;
    String city;
    LocalDate dob;
    String numberPhone;
    Long gender;
    List<String> roles;
}
