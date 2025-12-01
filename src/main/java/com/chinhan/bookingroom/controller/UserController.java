package com.chinhan.bookingroom.controller;


import com.chinhan.bookingroom.dto.ApiResponse;
import com.chinhan.bookingroom.dto.PageResponse;
import com.chinhan.bookingroom.dto.request.UserCreationRequest;
import com.chinhan.bookingroom.dto.request.UserUpdateAvatarRequest;
import com.chinhan.bookingroom.dto.request.UserUpdateRequest;
import com.chinhan.bookingroom.dto.response.UserResponse;
import com.chinhan.bookingroom.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/users")
public class UserController {
    UserService userService;

    @PostMapping
    public ApiResponse<UserResponse> createUser(@Valid @ModelAttribute UserCreationRequest request) throws IOException {
        return ApiResponse.<UserResponse>builder()
                .result(userService.createUser(request))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<UserResponse> updateUser(@PathVariable String id, @ModelAttribute UserUpdateRequest request) throws IOException {
        return ApiResponse.<UserResponse>builder()
                .result(userService.updateUser(id,request))
                .build();
    }

    @PutMapping("/avatar/{id}")
    public ApiResponse<UserResponse> updateAvatarUser(@PathVariable String id, @ModelAttribute UserUpdateAvatarRequest request) throws IOException {
        return ApiResponse.<UserResponse>builder()
                .result(userService.updateAvatar(id,request))
                .build();
    }

    @GetMapping
    public ApiResponse<PageResponse<UserResponse>> getAllUsers(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size){
        return ApiResponse.<PageResponse<UserResponse>>builder()
                .result(userService.getAllUsers(page, size))
                .build();
    }

    @GetMapping("/{userId}")
    public ApiResponse<UserResponse> getUser(@PathVariable String userId){
        return ApiResponse.<UserResponse>builder()
                .result(userService.getUserById(userId))
                .build();
    }

    @GetMapping("/myInfo")
    public ApiResponse<UserResponse> getMyInfo(){
        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteUser(@PathVariable String id){
        userService.deleteUser(id);
        return ApiResponse.<String>builder()
                .result("Delete User successful")
                .build();
    }

}
