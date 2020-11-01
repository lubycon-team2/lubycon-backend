package com.rubycon.rubyconteam2.domain.party_join.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.rubycon.rubyconteam2.domain.party_join.domain.PartyJoin;
import com.rubycon.rubyconteam2.domain.party.domain.PartyState;
import com.rubycon.rubyconteam2.domain.party.domain.ServiceType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.rubycon.rubyconteam2.domain.party_join.domain.QPartyJoin.partyJoin;

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

    public Optional<PartyJoin> findByUserIdAndTypeAndState(Long userId, ServiceType serviceType, PartyState partyState){
        PartyJoin pj = queryFactory
                .selectFrom(partyJoin)
                .where(partyJoin.user.userId.eq(userId))
                .where(partyJoin.party.serviceType.eq(serviceType))
                .where(partyJoin.party.partyState.eq(partyState))
                .where(partyJoin.isDeleted.eq(false))
                .fetchOne();
        return Optional.ofNullable(pj);
    }


    // TODO : N+1 쿼리 나는 듯 (최대 4개이지만)
    public List<PartyJoin> findAllMyPartyByState(Long userId, PartyState partyState) {
        return queryFactory.selectFrom(partyJoin)
                .where(partyJoin.user.userId.eq(userId))
                .where(partyJoin.party.partyState.eq(partyState))
                .fetch();
    }

    public List<PartyJoin> findAllByPartyId(Long partyId){
        return queryFactory.selectFrom(partyJoin)
                .where(partyJoin.party.partyId.eq(partyId))
                .fetch();
    }
}