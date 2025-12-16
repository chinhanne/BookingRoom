package com.chinhan.bookingroom.controller;

import com.chinhan.bookingroom.dto.ApiResponse;
import com.chinhan.bookingroom.dto.PageResponse;
import com.chinhan.bookingroom.dto.request.AmenityRequest;
import com.chinhan.bookingroom.dto.request.RoomSearchRequest;
import com.chinhan.bookingroom.dto.response.AmenityResponse;
import com.chinhan.bookingroom.dto.response.RoomResponse;
import com.chinhan.bookingroom.service.AmenityService;
import com.chinhan.bookingroom.service.SearchRoomService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoomSearchController {
    SearchRoomService searchRoomService;

    @PostMapping
    public ApiResponse<List<RoomResponse>> searchRooms(@RequestBody RoomSearchRequest request){
        return ApiResponse.<List<RoomResponse>>builder()
                .result(searchRoomService.searchAvailableRooms(request))
                .build();
    }


}
