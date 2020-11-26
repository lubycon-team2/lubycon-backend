package com.rubycon.rubyconteam2.domain.party.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.rubycon.rubyconteam2.domain.party.domain.Party;
import com.rubycon.rubyconteam2.domain.party.domain.ServiceType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

import static com.rubycon.rubyconteam2.domain.party.domain.QParty.party;

@Repository
public class PartyQueryRepository extends QuerydslRepositorySupport {
    private JPAQueryFactory queryFactory;

    public PartyQueryRepository(JPAQueryFactory queryFactory) {
        super(Party.class);
        this.queryFactory = queryFactory;
    }

    public Page<Party> findByServiceTypeIs(ServiceType serviceType, Pageable pageable) {
        JPAQuery<Party> query = queryFactory
                .selectFrom(party)
                .where(party.serviceType.eq(serviceType));

        Querydsl querydsl = Objects.requireNonNull(getQuerydsl());
        List<Party> partyList = querydsl.applyPagination(pageable, query).fetch();
        return new PageImpl<>(partyList, pageable, query.fetchCount());
    }
}
