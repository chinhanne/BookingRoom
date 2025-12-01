package com.chinhan.bookingroom.dto.request;

import com.chinhan.bookingroom.validator.DobConstraint;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    String username;
    @Size(min = 6, message = "INVALID_PASSWORD")
    String password;
    String firstname;
    String lastname;
    String city;
    String numberPhone;
//    @Min(value = 0, message = "MIN_GENDER")
//    @Max(value = 1, message = "MAX_GENDER")
    Long gender;
    MultipartFile image;
    @DobConstraint(min = 18, message = "INVALID_DOB")
    LocalDate dob;
}
