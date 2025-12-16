package com.chinhan.bookingroom.service;

import com.chinhan.bookingroom.dto.request.RoomSearchRequest;
import com.chinhan.bookingroom.dto.response.RoomResponse;
import com.chinhan.bookingroom.entity.Amenity;
import com.chinhan.bookingroom.entity.Room;
import com.chinhan.bookingroom.enums.BookingStatus;
import com.chinhan.bookingroom.mapper.RoomMapper;
import com.chinhan.bookingroom.repository.RoomRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SearchRoomService {
    RoomRepository roomRepository;
    RoomMapper roomMapper;

    private boolean matchRoomType(Room room, RoomSearchRequest request){
        if(request.getRoomTypeId() == null){
            return true;
        }
        return room.getRoomType().getId().equals(request.getRoomTypeId());
    }

    private boolean matchAmenities(Room room, RoomSearchRequest request){
        if(request.getAmenitieId() == null || request.getAmenitieId().isEmpty()){
            return true;
        }
        Set<String> amenities = room.getAmenities().stream().map(Amenity::getId).collect(Collectors.toSet());

        return amenities.containsAll(request.getAmenitieId());
    }

    private boolean matchPrice(Room room, RoomSearchRequest request){
        if(request.getMaxPrice() != null && room.getPrice().compareTo(request.getMaxPrice()) >= 0){
            return false;
        }

        if(request.getMinPrice() != null && room.getPrice().compareTo(request.getMinPrice()) <= 0){
            return false;
        }
        return true;
    }

    private boolean matchAvailable(Room room, LocalDateTime checkIn, LocalDateTime checkOut){
        return room.getBookings().stream().filter(b -> b.getStatus() == BookingStatus.CONFIRMED)
                .noneMatch(b -> b.getCheckIn().isBefore(checkOut) && b.getCheckOut().isAfter(checkIn));
    }

    public List<RoomResponse> searchAvailableRooms(RoomSearchRequest request){
        List<Room> rooms = roomRepository.findAll();

        return rooms.stream()
                .filter(r -> matchPrice(r, request))
                .filter(r -> matchAmenities(r, request))
                .filter(r -> matchRoomType(r, request))
                .filter(r -> matchAvailable(r, request.getCheckIn(), request.getCheckOut()))
                .map(room -> roomMapper.toRoomResponse(room)).toList();

    }
}
