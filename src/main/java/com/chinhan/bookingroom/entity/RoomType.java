package com.chinhan.bookingroom.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class RoomType {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String name;
    String description;
    boolean statusDelete = false;
    LocalDateTime createAt;
    LocalDateTime updateAt;

    @OneToMany(mappedBy = "roomType",cascade = CascadeType.ALL, orphanRemoval = true)
    List<Room> rooms;

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
