package com.chinhan.bookingroom.mapper;

import com.chinhan.bookingroom.dto.request.RoleRequest;
import com.chinhan.bookingroom.dto.response.RoleResponse;
import com.chinhan.bookingroom.entity.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role toRole(RoleRequest request);
    RoleResponse toRoleResponse(Role role);
}
