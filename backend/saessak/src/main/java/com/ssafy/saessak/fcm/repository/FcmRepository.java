package com.ssafy.saessak.fcm.repository;

import com.ssafy.saessak.fcm.domain.FcmToken;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FcmRepository extends CrudRepository<FcmToken, Long> {

}
