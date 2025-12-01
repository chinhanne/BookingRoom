package com.chinhan.bookingroom.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String path;
    @ManyToOne
    @JoinColumn(name = "room_id")
    Room room;
}
