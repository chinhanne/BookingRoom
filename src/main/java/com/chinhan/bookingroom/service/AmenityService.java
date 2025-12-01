package com.chinhan.bookingroom.service;

import com.chinhan.bookingroom.dto.PageResponse;
import com.chinhan.bookingroom.dto.request.AmenityRequest;
import com.chinhan.bookingroom.dto.response.AmenityResponse;
import com.chinhan.bookingroom.entity.Amenity;
import com.chinhan.bookingroom.exception.AppException;
import com.chinhan.bookingroom.exception.ErrorCode;
import com.chinhan.bookingroom.mapper.AmenityMapper;
import com.chinhan.bookingroom.repository.AmenityRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AmenityService {
    AmenityRepository amenityRepository;
    AmenityMapper amenityMapper;

    @PreAuthorize("hasRole('ADMIN')")
    public AmenityResponse createAmenity(AmenityRequest request){
        var amenities = amenityMapper.toAmenity(request);
        return amenityMapper.toAmenityResponse(amenityRepository.save(amenities));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public AmenityResponse updateAmenity(String id, AmenityRequest request){
        Amenity amenity = amenityRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.AMENITY_NOT_EXISTED));
        amenityMapper.updateAmenity(amenity,request);
        return amenityMapper.toAmenityResponse(amenityRepository.save(amenity));
    }

    public PageResponse<AmenityResponse> getAllAmenity(int page, int size){
        Sort sort = Sort.by("name").descending();
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        var pageData = amenityRepository.findAll(pageable);
        return PageResponse.<AmenityResponse>builder()
                .currentPage(page)
                .totalPage(pageData.getTotalPages())
                .pageSize(pageData.getSize())
                .totalElement(pageData.getTotalElements())
                .data(pageData.getContent().stream().map(amenityMapper::toAmenityResponse).toList())
                .build();
    }

    public AmenityResponse getAmenityById(String id){
        return amenityMapper.toAmenityResponse(amenityRepository.findById(id).orElseThrow(() ->
                new AppException(ErrorCode.AMENITY_NOT_EXISTED)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteAmenity(String id){
        amenityRepository.deleteById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteSoftAmenity(String id){
        Amenity amenity = amenityRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.AMENITY_NOT_EXISTED));
        amenity.setStatusDelete(true);
        amenityRepository.save(amenity);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void restoreAmenity(String id){
        Amenity amenity = amenityRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.AMENITY_NOT_EXISTED));
        amenity.setStatusDelete(false);
        amenityRepository.save(amenity);
    }

}
