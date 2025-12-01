package com.chinhan.bookingroom.mapper;

import com.chinhan.bookingroom.dto.request.UserCreationRequest;
import com.chinhan.bookingroom.dto.request.UserUpdateRequest;
import com.chinhan.bookingroom.dto.response.UserResponse;
import com.chinhan.bookingroom.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
    @Mapping(target = "image", ignore = true)
    User toUser(UserCreationRequest request);

    UserResponse toUserResponse(User user);

    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "image", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
