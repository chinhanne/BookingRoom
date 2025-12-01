package com.chinhan.bookingroom.controller;

import com.chinhan.bookingroom.dto.ApiResponse;
import com.chinhan.bookingroom.dto.PageResponse;
import com.chinhan.bookingroom.dto.request.RoomTypeRequest;
import com.chinhan.bookingroom.dto.response.RoomTypeResponse;
import com.chinhan.bookingroom.service.RoomtypeService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/room_type")
public class RoomTypeController {
    RoomtypeService roomtypeService;

    @PostMapping
    public ApiResponse<RoomTypeResponse> creaeteRoomType(@Valid @RequestBody RoomTypeRequest request){
        return ApiResponse.<RoomTypeResponse>builder()
                .result(roomtypeService.createRoomType(request))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<RoomTypeResponse> updateRoomType(@PathVariable String id, @RequestBody RoomTypeRequest request){
        return  ApiResponse.<RoomTypeResponse>builder()
                .result(roomtypeService.updateRoomType(id, request))
                .build();
    }

    @GetMapping
    public ApiResponse<PageResponse<RoomTypeResponse>> getAllRoomType(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size)
    {
        return ApiResponse.<PageResponse<RoomTypeResponse>>builder()
                .result(roomtypeService.getAllRoomType(page, size))
                .build();

    }

    @GetMapping("/{id}")
    public ApiResponse<RoomTypeResponse> getRoomTypeById(@PathVariable String id){
        return ApiResponse.<RoomTypeResponse>builder()
                .result(roomtypeService.getRoomTypeById(id))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteRoomType(@PathVariable String id){
        roomtypeService.deleteRoomtype(id);
        return ApiResponse.<String>builder()
                .result("Delete successful")
                .build();
    }

    @PutMapping("/delete_soft/{id}")
    public ApiResponse<String> deleteSoftRoomType(@PathVariable String id){
        roomtypeService.deleteSoftRoomType(id);
        return ApiResponse.<String>builder()
                .result("Delete successful")
                .build();
    }

    @PutMapping("/restore/{id}")
    public ApiResponse<String> restoreRoomType(@PathVariable String id){
        roomtypeService.restoreRoomType(id);
        return ApiResponse.<String>builder()
                .result("Restore successful")
                .build();
    }

}
