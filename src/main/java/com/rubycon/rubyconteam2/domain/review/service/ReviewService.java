package com.rubycon.rubyconteam2.domain.review.service;

import com.rubycon.rubyconteam2.domain.party.domain.Party;
import com.rubycon.rubyconteam2.domain.party.domain.PartyState;
import com.rubycon.rubyconteam2.domain.party.exception.PartyNotFoundException;
import com.rubycon.rubyconteam2.domain.party.repository.PartyRepository;
import com.rubycon.rubyconteam2.domain.review.domain.Rating;
import com.rubycon.rubyconteam2.domain.review.domain.Review;
import com.rubycon.rubyconteam2.domain.review.dto.request.ReviewRequest;
import com.rubycon.rubyconteam2.domain.review.exception.IsNotReviewableException;
import com.rubycon.rubyconteam2.domain.review.exception.ReviewDuplicatedException;
import com.rubycon.rubyconteam2.domain.review.repository.ReviewQueryRepository;
import com.rubycon.rubyconteam2.domain.review.repository.ReviewRepository;
import com.rubycon.rubyconteam2.domain.user.domain.User;
import com.rubycon.rubyconteam2.domain.user.dto.response.ProfileReviewResponse;
import com.rubycon.rubyconteam2.domain.user.exception.UserNotFoundException;
import com.rubycon.rubyconteam2.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewQueryRepository reviewQueryRepository;
    private final UserRepository userRepository;
    private final PartyRepository partyRepository;

    /**
     * 특정 사용자에게 리뷰하기
     * TODO : 같은 파티인지, 리뷰 가능한지 (탈퇴 시점, 참여 시점 비교?) 
     * @param reviewerId : 리뷰 하는 사용자 ID (현재 사용자)
     * @param targetId : 리뷰 할 사용자 ID
     * @param reviewDto : 리뷰 내용 DTO
     */
    @Transactional
    public void review(Long reviewerId, Long targetId, Long partyId, ReviewRequest reviewDto) {
        User reviewer = userRepository.findById(reviewerId)
                .orElseThrow(UserNotFoundException::new);
        User target = userRepository.findById(targetId)
                .orElseThrow(UserNotFoundException::new);
        Party party = partyRepository.findById(partyId)
                .orElseThrow(PartyNotFoundException::new);

        PartyState partyState = party.getPartyState();
        if (partyState.isNotReviewable()) throw new IsNotReviewableException();

        Optional<Review> optional = reviewQueryRepository.exists(reviewerId, targetId, partyId);
        if (optional.isPresent()) throw new ReviewDuplicatedException();

        Review review = Review.of(reviewer, target, party);

        List<Rating> ratingList = reviewDto.getRatingList(review);
        ratingList.forEach(rating -> review.getRatings().add(rating));

        reviewRepository.save(review);
    }

    /**
     * 특정 사용자의 모든 리뷰 조회
     * @param targetId 리뷰 조회할 사용자 ID
     * @return
     */
    @Transactional(readOnly = true)
    public ProfileReviewResponse findAllReview(Long targetId){
        List<Review> reviews = reviewQueryRepository.findAllReviewByTargetId(targetId);
        return new ProfileReviewResponse(reviews);
    }
}
