package com.chinhan.bookingroom.exception;

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
    USER_NOT_EXISTED(1003,"Người dùng không tồn tại",HttpStatus.NOT_FOUND),
    INVALID_PASSWORD(1004,"Mat khau qua ngan", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1005,"Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1006,"You do not have permission ", HttpStatus.FORBIDDEN),
    INVALID_DOB(1007,"You age must be at least {min} ", HttpStatus.BAD_REQUEST),
    INVALID_TOKEN(1008, "Invalid token", HttpStatus.BAD_REQUEST),
    ROOMTYPE_NOT_EXISTED(1009, "Room type not existed", HttpStatus.BAD_REQUEST),
    AMENITY_NOT_EXISTED(1010, "Amenity not existed", HttpStatus.BAD_REQUEST),
    ROOM_NOT_EXISTED(1011, "Room not existed", HttpStatus.BAD_REQUEST),
    LOCK_NOT_EXISTED(1012, "Lock not existed", HttpStatus.BAD_REQUEST),
    USER_NOT_LOGIN(1013, "User not logged in", HttpStatus.BAD_REQUEST),
    LOCK_EXPIRED(1014, "Lock expired", HttpStatus.BAD_REQUEST),
    ROOM_NOT_AVALIABLE(1015, "Room not avaliable", HttpStatus.BAD_REQUEST),
    PRICE_NOT_NULL(1016, "Price not null", HttpStatus.BAD_REQUEST),
    ;

    int code;
    String message;
    HttpStatus httpStatus;
}
