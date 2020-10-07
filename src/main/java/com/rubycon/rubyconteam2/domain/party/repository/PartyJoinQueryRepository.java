package com.rubycon.rubyconteam2.domain.party.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.rubycon.rubyconteam2.domain.party.domain.PartyJoin;
import com.rubycon.rubyconteam2.domain.party.domain.PartyState;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.rubycon.rubyconteam2.domain.party.domain.QPartyJoin.partyJoin;

/**
 * https://jojoldu.tistory.com/516
 * JPA exists 성능 개선
 */

@RequiredArgsConstructor
@Repository
public class PartyJoinQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Optional<PartyJoin> exists(Long userId, Long partyId) {
        PartyJoin pj = queryFactory
                .selectFrom(partyJoin)
                .where(partyJoin.user.userId.eq(userId))
                .where(partyJoin.party.partyId.eq(partyId))
                .fetchOne();
        return Optional.ofNullable(pj);
    }


    // N+1 쿼리 나는 듯 (최대 4개이지만)
    public List<PartyJoin> findMyPartyByState(Long userId, PartyState partyState) {
        return queryFactory.selectFrom(partyJoin)
                .where(partyJoin.user.userId.eq(userId))
                .where(partyJoin.party.partyState.eq(partyState))
                .fetch();
    }
}