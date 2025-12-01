package com.chinhan.bookingroom.entity;

import com.chinhan.bookingroom.enums.RoomStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String code;
    int floor;
    int capacity;
    String description;
    BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "room_type_id")
    RoomType roomType;

    @ManyToMany
    @JoinTable(
            name = "amenities_room",
            joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "amenity_id")
    )
    Set<Amenity> amenities;

    boolean statusDelete = false;
    LocalDateTime createAt;
    LocalDateTime updateAt;
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Image> images;

    @Enumerated(EnumType.STRING)
    RoomStatus status;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Booking> bookings;

    @PrePersist
    public void prePersist(){
        createAt = LocalDateTime.now();
        updateAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate(){
        updateAt = LocalDateTime.now();
    }

}
