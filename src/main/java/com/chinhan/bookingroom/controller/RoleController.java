package com.chinhan.bookingroom.controller;

import com.chinhan.bookingroom.dto.ApiResponse;
import com.chinhan.bookingroom.dto.request.RoleRequest;
import com.chinhan.bookingroom.dto.response.RoleResponse;
import com.chinhan.bookingroom.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/roles")
public class RoleController {
    RoleService roleService;

    @PostMapping
    ApiResponse<RoleResponse> createRole(@RequestBody RoleRequest request){
        return ApiResponse.<RoleResponse>builder()
                .result(roleService.createRole(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<RoleResponse>> getAll(){
        return ApiResponse.<List<RoleResponse>>builder()
                .result(roleService.getAllRole())
                .build();
    }

    @DeleteMapping("/{role}")
    ApiResponse<String> deletePermission(@PathVariable String role){
        roleService.deleteRole(role);
        return ApiResponse.<String>builder()
                .result("Delete role successful")
                .build();
    }
}
