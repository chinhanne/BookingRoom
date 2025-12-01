package com.chinhan.bookingroom.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Collections;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PageResponse<T> {
    int currentPage;
    int totalPage;
    int pageSize;
    long totalElement;

    @Builder.Default
    List<T> data = Collections.emptyList();
}
