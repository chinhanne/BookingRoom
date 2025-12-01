package com.chinhan.bookingroom.repository;

import com.chinhan.bookingroom.entity.RedisInvalidatedToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisInvalidatedTokenRepository extends CrudRepository<RedisInvalidatedToken, String> {
}
