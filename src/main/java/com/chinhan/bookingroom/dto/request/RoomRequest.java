package com.chinhan.bookingroom.dto.request;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoomRequest {
    String code;
    int floor;
    int capacity;
    BigDecimal price;
    String description;
    String roomTypeId;
    List<String> amenityId;
    List<MultipartFile> images;
}
