package com.chinhan.bookingroom.service;

import com.chinhan.bookingroom.dto.PageResponse;
import com.chinhan.bookingroom.dto.request.RoomRequest;
import com.chinhan.bookingroom.dto.response.RoomResponse;
import com.chinhan.bookingroom.entity.Amenity;
import com.chinhan.bookingroom.entity.Image;
import com.chinhan.bookingroom.entity.Room;
import com.chinhan.bookingroom.entity.RoomType;
import com.chinhan.bookingroom.exception.AppException;
import com.chinhan.bookingroom.exception.ErrorCode;
import com.chinhan.bookingroom.mapper.RoomMapper;
import com.chinhan.bookingroom.repository.AmenityRepository;
import com.chinhan.bookingroom.repository.RoomRepository;
import com.chinhan.bookingroom.repository.RoomTypeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoomService {
     RoomRepository roomRepository;
     RoomMapper roomMapper;
     RoomTypeRepository roomTypeRepository;
     AmenityRepository amenityRepository;

     @PreAuthorize("hasRole('ADMIN')")
     public RoomResponse createRoom(RoomRequest request) throws IOException {
         try {
             var room = roomMapper.toRoom(request);

             RoomType roomType = roomTypeRepository.findById(request.getRoomTypeId()).orElseThrow(() ->
                     new AppException(ErrorCode.ROOMTYPE_NOT_EXISTED));
             room.setRoomType(roomType);

             Set<Amenity> amenities = new HashSet<>(amenityRepository.findAllById(request.getAmenityId()));
             room.setAmenities(amenities);

             room.setImages(new ArrayList<>());// khoi tao list de tranh bi null
             if(request.getImages() != null && !request.getImages().isEmpty()){
                 for(MultipartFile file : request.getImages()){
                     String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
                     Path path = Paths.get("D:/Java_SpringBoot/images/" + filename);
                     Files.createDirectories(path.getParent());
                     file.transferTo(path.toFile());
                     Image image = new Image();
                     image.setPath("/booking/images/" + filename);
                     image.setRoom(room);
                     room.getImages().add(image);
                 }
             }
             return roomMapper.toRoomResponse(roomRepository.save(room));
         } catch (Exception ex) {
             log.error("Lỗi khi tạo room: ", ex);
             throw ex;
         }
     }

     @PreAuthorize("hasRole('ADMIN')")
     public RoomResponse updateRoom(String id, RoomRequest request) throws IOException {
         Room room = roomRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ROOM_NOT_EXISTED));
         roomMapper.updateRoom(room, request);

         RoomType roomType = roomTypeRepository.findById(request.getRoomTypeId()).orElseThrow(() ->
                 new AppException(ErrorCode.ROOMTYPE_NOT_EXISTED));
         room.setRoomType(roomType);

         Set<Amenity> amenities = new HashSet<>(amenityRepository.findAllById(request.getAmenityId()));
         room.setAmenities(amenities);

         if (request.getImages() != null && !request.getImages().isEmpty()) {
             for (MultipartFile file : request.getImages()) {
                 String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
                 String fullPath = "D:/Java_SpringBoot/images/" + fileName;

                 file.transferTo(Paths.get(fullPath));

                 Image image = Image.builder()
                         .path("/booking/images/" + fileName)
                         .room(room)
                         .build();

                 room.getImages().add(image);
             }
         }

         roomRepository.save(room);
         return roomMapper.toRoomResponse(room);
     }

     public PageResponse<RoomResponse> getAllRoom(int page, int size){
         Sort sort = Sort.by("code").descending();
         Pageable pageable = PageRequest.of(page - 1, size, sort);
         var pageData = roomRepository.findAll(pageable);
         return PageResponse.<RoomResponse>builder()
                 .currentPage(page)
                 .pageSize(pageData.getSize())
                 .totalElement(pageData.getTotalElements())
                 .totalPage(pageData.getTotalPages())
                 .data(pageData.getContent().stream().map(roomMapper::toRoomResponse).toList())
                 .build();
     }

     public RoomResponse getRoomById(String id){
         return roomMapper.toRoomResponse( roomRepository.findById(id).orElseThrow(() ->
                 new AppException(ErrorCode.ROOM_NOT_EXISTED)));
     }

    @PreAuthorize("hasRole('ADMIN')")
     public void deleteRoom(String id){
         roomRepository.deleteById(id);
     }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteSoftRoom(String id){
        Room room = roomRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ROOM_NOT_EXISTED));
        room.setStatusDelete(true);
        roomRepository.save(room);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void restoreRoom(String id){
        Room room = roomRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ROOM_NOT_EXISTED));
        room.setStatusDelete(false);
        roomRepository.save(room);
    }
}
