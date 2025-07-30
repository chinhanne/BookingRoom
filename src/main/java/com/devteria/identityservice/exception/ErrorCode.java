package com.devteria.identityservice.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum ErrorCode {

    UNCATEGORIZED_EXCEPTION(9999,"Lỗi không xác định",HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001,"Thông tin khóa bị sai",HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002,"Người dùng đã tồn tại",HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1003,"Người dùng không tồn tại",HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1004,"Mat khau qua ngan", HttpStatus.BAD_REQUEST)
    ;

    int code;
    String message;
    HttpStatus httpStatus;
}
