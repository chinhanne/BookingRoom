package com.chinhan.bookingroom.repository;

import com.chinhan.bookingroom.entity.RedisRoomLock;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LockRoomRepository extends CrudRepository<RedisRoomLock, String> {
    List<RedisRoomLock> findByRoomId(String roomId);
}
