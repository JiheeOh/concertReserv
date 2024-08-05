package com.hhplus.concertReserv.infrastructure.token.impl;

import com.hhplus.concertReserv.domain.token.entity.Token;
import com.hhplus.concertReserv.domain.token.repositories.TokenRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Repository
public class TokenRedisRepositoryImpl implements TokenRepository {

    private final RedisTemplate<String, Object> redisTemplate;

    private static final String WAIT_KEY = "waitToken";
    private static final String ACTIVATE_KEY = "activateToken";

    public TokenRedisRepositoryImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 대기열에 토큰 등록
     *
     * @param token
     */
    @Override
    public void save(Token token) {
        // LocalDateTime -> Long 타입으로 변환
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss.SSS");
        String formattedDateTime = localDateTime.format(formatter);

        // 문자열을 double로 변환
        double dateTimeAsDouble = Double.parseDouble(formattedDateTime);

        token.setCreateDt(dateTimeAsDouble);

        ZSetOperations<String, Object> zSetOps = redisTemplate.opsForZSet();
        zSetOps.add(WAIT_KEY, token.toString(), token.getCreateDt());

    }

    /**
     * 토큰 활성화하기
     *
     * @param count
     */
    @Override
    public void activateToken(Long count,int timeOut) {
        ZSetOperations<String, Object> zSetOps = redisTemplate.opsForZSet();
        // TODO 활성화할 애들 : 애초에 String 가져와서 -> set을 바로 만들면 낫지 않나
        Set<ZSetOperations.TypedTuple<Object>> toActivate = zSetOps.popMin(WAIT_KEY, count);

        if(toActivate !=null){
            // Zset -> set
            SetOperations<String, Object> setOps = redisTemplate.opsForSet();
            for (ZSetOperations.TypedTuple<Object> content : toActivate) {
                String setContent = Objects.requireNonNull(content.getValue()).toString();
                setOps.add(ACTIVATE_KEY, setContent);

            }
            redisTemplate.expire(ACTIVATE_KEY, timeOut, TimeUnit.MINUTES);
        }

    }

    /**
     * 활성화된 토큰인지 확인
     *
     * @param token 토큰정보
     * @return 활성화 : true, 비활성화 : false
     */
    @Override
    public boolean findActivateToken(Token token) {
        return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(ACTIVATE_KEY, token.toString()));
    }


    /**
     * 결제 완료된 토큰 삭제
     *
     * @param token 토큰 정보
     */
    @Override
    public void deactivateToken(Token token) {
        redisTemplate.opsForSet().remove(ACTIVATE_KEY, token.toString());
    }


}
