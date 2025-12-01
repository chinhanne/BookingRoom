package com.chinhan.bookingroom.controller;

import com.chinhan.bookingroom.dto.ApiResponse;
import com.chinhan.bookingroom.dto.PageResponse;
import com.chinhan.bookingroom.dto.request.RoomRequest;
import com.chinhan.bookingroom.dto.response.RoomResponse;
import com.chinhan.bookingroom.service.RoomService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoomController {
    RoomService roomService;



    @PostMapping
    public ApiResponse<RoomResponse> createRoom(@ModelAttribute RoomRequest request) throws IOException {
        return ApiResponse.<RoomResponse>builder()
                .result(roomService.createRoom(request))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<RoomResponse> updateRoom(@PathVariable String id, @ModelAttribute RoomRequest request) throws IOException {
        return ApiResponse.<RoomResponse>builder()
                .result(roomService.updateRoom(id,request))
                .build();
    }

    @GetMapping
    public ApiResponse<PageResponse<RoomResponse>> getAllRoom(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size)
    {
        return ApiResponse.<PageResponse<RoomResponse>>builder()
                .result(roomService.getAllRoom(page, size))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<RoomResponse> getRoomById(@PathVariable String id){
        return ApiResponse.<RoomResponse>builder()
                .result(roomService.getRoomById(id))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteRoom(@PathVariable String id){
        roomService.deleteRoom(id);
        return ApiResponse.<String>builder()
                .result("Deleted successful")
                .build();
    }

    @DeleteMapping("/delete_soft/{id}")
    public ApiResponse<String> deleteSoftRoom(@PathVariable String id){
        roomService.deleteSoftRoom(id);
        return ApiResponse.<String>builder()
                .result("Deleted successful")
                .build();
    }

    @DeleteMapping("/restore/{id}")
    public ApiResponse<String> restoreRoom(@PathVariable String id){
        roomService.restoreRoom(id);
        return ApiResponse.<String>builder()
                .result("Restore successful")
                .build();
    }
}
