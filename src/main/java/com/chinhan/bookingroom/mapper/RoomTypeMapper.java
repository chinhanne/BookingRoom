package com.chinhan.bookingroom.mapper;

import com.chinhan.bookingroom.dto.request.RoomTypeRequest;
import com.chinhan.bookingroom.dto.response.RoomTypeResponse;
import com.chinhan.bookingroom.entity.RoomType;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",  nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RoomTypeMapper {
    RoomType toRoomType(RoomTypeRequest request);
    RoomTypeResponse toRoomTypeResponse(RoomType roomType);
    void updateRoomType(@MappingTarget RoomType roomType, RoomTypeRequest request);
}
