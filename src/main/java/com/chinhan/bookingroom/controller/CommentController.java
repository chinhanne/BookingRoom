package com.chinhan.bookingroom.controller;

import com.chinhan.bookingroom.dto.ApiResponse;
import com.chinhan.bookingroom.dto.PageResponse;
import com.chinhan.bookingroom.dto.request.AmenityRequest;
import com.chinhan.bookingroom.dto.response.AmenityResponse;
import com.chinhan.bookingroom.service.AmenityService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/amenity")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AmenityController {
    AmenityService amenityService;

    @PostMapping
    public ApiResponse<AmenityResponse> createAmenity(@RequestBody AmenityRequest request){
        return ApiResponse.<AmenityResponse>builder()
                .result(amenityService.createAmenity(request))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<AmenityResponse> updateAmenity(@PathVariable String id, @RequestBody AmenityRequest request){
        return ApiResponse.<AmenityResponse>builder()
                .result(amenityService.updateAmenity(id, request))
                .build();
    }

    @GetMapping
    public ApiResponse<PageResponse<AmenityResponse>> getAllAmenity(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size
    ){
        return ApiResponse.<PageResponse<AmenityResponse>>builder()
                .result(amenityService.getAllAmenity(page, size))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<AmenityResponse> getAmenityById(@PathVariable String id){
        return ApiResponse.<AmenityResponse>builder()
                .result(amenityService.getAmenityById(id))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteAmenity(@PathVariable String id){
        amenityService.deleteAmenity(id);
        return ApiResponse.<String>builder()
                .result("Deleted successful")
                .build();
    }

    @PutMapping("/delete_soft/{id}")
    public ApiResponse<String> deleteSoftAmenity(@PathVariable String id){
        amenityService.deleteSoftAmenity(id);
        return ApiResponse.<String>builder()
                .result("Deleted successful")
                .build();
    }

    @PutMapping("/restore/{id}")
    public ApiResponse<String> restoreAmenity(@PathVariable String id){
        amenityService.restoreAmenity(id);
        return ApiResponse.<String>builder()
                .result("Restore successful")
                .build();
    }
}
