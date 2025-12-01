package com.chinhan.bookingroom.mapper;

import com.chinhan.bookingroom.dto.request.RoomRequest;
import com.chinhan.bookingroom.dto.response.RoomResponse;
import com.chinhan.bookingroom.entity.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",  nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RoomMapper {
    Room toRoom(RoomRequest request);

    @Mapping(source = "roomType.name", target = "roomTypeName")
    @Mapping(target = "amenities",
            expression = "java(room.getAmenities().stream().map(a -> a.getName()).toList())")
    @Mapping(target = "images", expression = "java(room.getImages().stream().map(image -> image.getPath()).toList())")
    RoomResponse toRoomResponse(Room room);

    @Mapping(target = "images", ignore = true)
    void updateRoom(@MappingTarget Room room, RoomRequest request);

}
