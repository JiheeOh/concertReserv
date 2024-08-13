package com.hhplus.concertReserv.infrastructure.redis.token.redisRepository;

import com.hhplus.concertReserv.domain.token.entity.Token;
import org.springframework.data.repository.CrudRepository;

public interface TokenRedisRepository extends CrudRepository<Token,Long> {
}
