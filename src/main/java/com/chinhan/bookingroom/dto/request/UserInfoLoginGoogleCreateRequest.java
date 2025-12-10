package com.dacn.WebsiteBanDoCongNghe.dto.request;

import com.dacn.WebsiteBanDoCongNghe.validator.DobConstraint;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserInfoLoginGoogleCreateRequest {

    @NotBlank(message = "PASSWORD_NOT_NULL")
    @Size(min = 8, message = "PASSWORD_INVALID")
    String password;
    @DobConstraint(min = 18, message = "INVALID_DOB")
    LocalDate dob;
    @Min(value = 0, message = "MIN_GENDER")
    @Max(value = 1, message = "MAX_GENDER")
    Long gender;
    @NotBlank(message = "ADDRESS_NOT_NULL")
    String address;
    @NotBlank(message = "NUMBERPHONE_NOT_NULL")
    String numberPhone;
}
