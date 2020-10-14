package com.rubycon.rubyconteam2.domain.review.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.rubycon.rubyconteam2.domain.party.domain.PartyJoin;
import com.rubycon.rubyconteam2.domain.review.domain.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.rubycon.rubyconteam2.domain.review.domain.QReview.review;

@RequiredArgsConstructor
@Repository
public class ReviewQueryRepository {
    private final JPAQueryFactory queryFactory;

    public Optional<Review> exists(Long reviewerId, Long targetId) {
        Review rv = queryFactory
                .selectFrom(review)
                .where(review.reviewer.userId.eq(reviewerId))
                .where(review.target.userId.eq(targetId))
                .fetchOne();
        return Optional.ofNullable(rv);
    }
}
