package com.chinhan.bookingroom.mapper;

import com.chinhan.bookingroom.dto.request.AmenityRequest;
import com.chinhan.bookingroom.dto.response.AmenityResponse;
import com.chinhan.bookingroom.entity.Amenity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",  nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AmenityMapper {
    Amenity toAmenity(AmenityRequest request);
    AmenityResponse toAmenityResponse(Amenity amenity);
    void updateAmenity(@MappingTarget Amenity amenity, AmenityRequest request);
}
