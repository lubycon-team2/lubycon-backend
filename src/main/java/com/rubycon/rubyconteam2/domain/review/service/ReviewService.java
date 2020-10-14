package com.rubycon.rubyconteam2.domain.review.service;

import com.rubycon.rubyconteam2.domain.party.domain.PartyJoin;
import com.rubycon.rubyconteam2.domain.party.exception.PartyJoinDuplicatedException;
import com.rubycon.rubyconteam2.domain.review.domain.Content;
import com.rubycon.rubyconteam2.domain.review.domain.Rating;
import com.rubycon.rubyconteam2.domain.review.domain.Review;
import com.rubycon.rubyconteam2.domain.review.dto.request.ReviewRequest;
import com.rubycon.rubyconteam2.domain.review.exception.ReviewDuplicatedException;
import com.rubycon.rubyconteam2.domain.review.repository.RatingRepository;
import com.rubycon.rubyconteam2.domain.review.repository.ReviewQueryRepository;
import com.rubycon.rubyconteam2.domain.review.repository.ReviewRepository;
import com.rubycon.rubyconteam2.domain.user.domain.User;
import com.rubycon.rubyconteam2.domain.user.exception.UserNotFoundException;
import com.rubycon.rubyconteam2.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewQueryRepository reviewQueryRepository;
    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;

    /**
     * 특정 사용자에게 리뷰하기
     * @param reviewerId : 리뷰 하는 사용자 ID (현재 사용자)
     * @param targetId : 리뷰 할 사용자 ID
     * @param reviewDto : 리뷰 내용 DTO
     */
    @Transactional
    public void review(Long reviewerId, Long targetId, ReviewRequest reviewDto) {
        User reviewer = userRepository.findById(reviewerId)
                .orElseThrow(UserNotFoundException::new);
        User target = userRepository.findById(targetId)
                .orElseThrow(UserNotFoundException::new);

        Optional<Review> optional = reviewQueryRepository.exists(reviewerId, targetId);
        if (optional.isPresent()) throw new ReviewDuplicatedException();

        Review review = Review.of(reviewer, target, new ArrayList<>());

        List<Rating> ratingList = reviewDto.getRatingList(review);
        ratingList.forEach(rating -> review.getRating().add(rating));

        reviewRepository.save(review);
    }
}
