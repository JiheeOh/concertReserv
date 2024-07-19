package com.hhplus.concertReserv.infrastructure.token.jpaRepository;

import com.hhplus.concertReserv.domain.token.dto.TokenDto;
import com.hhplus.concertReserv.domain.token.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface TokenJPARepository extends JpaRepository<Token, Long> {
    @Query("select t from Token t where t.concertId = :concertId and t.status=1 order by t.memberId,t.concertId,t.createDt")
    Optional<Token> getLastOne(UUID concertId);

    @Query("select count(1) from Token  t where t.status = 0")
    int countActiveToken();

    @Query(value = "WITH SelectedTokens AS (" +
            "  SELECT id FROM Token WHERE status = 1 " +
            "  ORDER BY member_id, concert_id, create_dt " +
            "  LIMIT :count " +
            ") " +
            "UPDATE Token SET status = 0,activate_at = CURRENT_TIMESTAMP WHERE id IN (SELECT id FROM SelectedTokens)",
            nativeQuery = true)
    void updateTokenStatusActivate(int count);



    @Query("SELECT t FROM Token t WHERE t.status = 0 and t.tokenId = :tokenId ")
    Optional<TokenDto> findActivateToken(Long tokenId);

    @Query("UPDATE Token t SET t.status = 2 WHERE t.tokenId=:tokenId  ")
    void updateTokenStatusDeactivate(Long tokenId);
    @Query("UPDATE Token t SET t.status = 2 WHERE t.tokenId IN :tokenId ")
    void updateTokenStatusDeactivate(List<Long> tokenId);

    @Query(value = "WITH SelectedTokens AS (" +
            "  SELECT id FROM Token WHERE status = 0 " +
            "  and ABS(MINUTE_BETWEEN(CURRENT_TIMESTAMP, activate_at)) = :dueTime; " +
            ") " +
            "UPDATE Token SET status = 2,activate_at = CURRENT_TIMESTAMP WHERE id IN (SELECT id FROM SelectedTokens)",
            nativeQuery = true)
    void updateTokenStatusDeactivate(int dueTime);
}

