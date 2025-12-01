package com.chinhan.bookingroom.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String id;
    String username;
    String password;
    String firstname;
    String lastname;
    String city;
    LocalDate dob;
    String numberPhone;
    String image;
    Long gender;
    Set<RoleResponse> roles;
}
