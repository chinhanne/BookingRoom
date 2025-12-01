package com.chinhan.bookingroom.service;

import com.chinhan.bookingroom.dto.PageResponse;
import com.chinhan.bookingroom.dto.request.RoomTypeRequest;
import com.chinhan.bookingroom.dto.response.RoomTypeResponse;
import com.chinhan.bookingroom.entity.RoomType;
import com.chinhan.bookingroom.exception.AppException;
import com.chinhan.bookingroom.exception.ErrorCode;
import com.chinhan.bookingroom.mapper.RoomTypeMapper;
import com.chinhan.bookingroom.repository.RoomTypeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class RoomtypeService {
    RoomTypeRepository roomTypeRepository;
    RoomTypeMapper roomTypeMapper;

    @PreAuthorize("hasRole('ADMIN')")
    public RoomTypeResponse createRoomType(RoomTypeRequest request){
        var type = roomTypeMapper.toRoomType(request);
        return roomTypeMapper.toRoomTypeResponse(roomTypeRepository.save(type));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public RoomTypeResponse updateRoomType(String id, RoomTypeRequest request){
        RoomType roomType = roomTypeRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ROOMTYPE_NOT_EXISTED));
        roomTypeMapper.updateRoomType(roomType,request);
        return roomTypeMapper.toRoomTypeResponse(roomTypeRepository.save(roomType));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public PageResponse<RoomTypeResponse> getAllRoomType(int page, int size){
        Sort sort = Sort.by("name").descending();
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        var pageData = roomTypeRepository.findAll(pageable);

        return PageResponse.<RoomTypeResponse>builder()
                .currentPage(page)
                .pageSize(pageData.getSize())
                .totalPage(pageData.getTotalPages())
                .totalElement(pageData.getTotalElements())
                .data(pageData.getContent().stream().map(roomTypeMapper::toRoomTypeResponse).toList())
                .build();
    }

    public RoomTypeResponse getRoomTypeById(String id){
        return roomTypeMapper.toRoomTypeResponse(roomTypeRepository.findById(id).orElseThrow(() ->
                new AppException(ErrorCode.ROOMTYPE_NOT_EXISTED)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteRoomtype(String id){
        roomTypeRepository.deleteById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteSoftRoomType(String id){
        RoomType roomType = roomTypeRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ROOMTYPE_NOT_EXISTED));
        roomType.setStatusDelete(true);
        roomTypeRepository.save(roomType);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void restoreRoomType(String id){
        RoomType roomType = roomTypeRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ROOMTYPE_NOT_EXISTED));
        roomType.setStatusDelete(false);
        roomTypeRepository.save(roomType);
    }

}
