package com.rubycon.rubyconteam2.domain.review.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.rubycon.rubyconteam2.domain.review.domain.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.rubycon.rubyconteam2.domain.review.domain.QReview.review;

@RequiredArgsConstructor
@Repository
public class ReviewQueryRepository {
    private final JPAQueryFactory queryFactory;

    public Optional<Review> exists(Long reviewerId, Long targetId, Long partyId) {
        Review rv = queryFactory
                .selectFrom(review)
                .where(review.reviewer.userId.eq(reviewerId))
                .where(review.target.userId.eq(targetId))
                .where(review.party.partyId.eq(partyId))
                .fetchOne();
        return Optional.ofNullable(rv);
    }

    // TODO : N+1 쿼리 나는 듯
    public List<Review> findAllReviewByTargetId(Long targetId){
        return queryFactory
                .selectFrom(review)
                .where(review.target.userId.eq(targetId))
                .fetch();
    }
}
