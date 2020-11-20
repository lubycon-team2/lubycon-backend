package com.rubycon.rubyconteam2.global.factory;

import com.rubycon.rubyconteam2.domain.party.domain.Party;
import com.rubycon.rubyconteam2.domain.party.domain.ServiceType;
import com.rubycon.rubyconteam2.domain.review.domain.Content;
import com.rubycon.rubyconteam2.domain.review.domain.Rating;
import com.rubycon.rubyconteam2.domain.review.domain.Review;
import com.rubycon.rubyconteam2.domain.review.dto.request.ReviewRequest;
import com.rubycon.rubyconteam2.domain.user.domain.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestReviewFactory {

    public static Review createReview(User reviewer, User target, Party party){
        return Review.of(reviewer, target, party);
    }

    public static Rating createRating(Content content, Review review){
        return Rating.of(content, review);
    }

    public static ReviewRequest createReviewDto(){
        List<String> contents = Arrays.asList(
                Content.COMPLIMENTS_1.toString(),
                Content.COMPLIMENTS_3.toString(),
                Content.COMPLIMENTS_5.toString()
        );
        return ReviewRequest.builder()
                .contents(contents)
                .build();
    };

    public static List<Rating> findAllComplimentsRatings(Review review){
        return new ArrayList<>(
                Arrays.asList(
                        createRating(Content.COMPLIMENTS_1, review),
                        createRating(Content.COMPLIMENTS_2, review),
                        createRating(Content.COMPLIMENTS_3, review),
                        createRating(Content.COMPLIMENTS_4, review)
                )
        );
    }

    public static List<Rating> findAllImprovementsRatings(Review review){
        return new ArrayList<>(
                Arrays.asList(
                        createRating(Content.IMPROVEMENTS_1, review),
                        createRating(Content.IMPROVEMENTS_2, review),
                        createRating(Content.IMPROVEMENTS_3, review),
                        createRating(Content.IMPROVEMENTS_4, review)
                )
        );
    }

    public static List<Review> findAllReviews(){
        Party party = TestPartyFactory.createParty(1L, ServiceType.NETFLIX);

        List<User> users = TestUserFactory.getTestUsers();
        User user1 = users.get(0);
        User user2 = users.get(1);
        User user3 = users.get(2);

        Review review1 = createReview(user2, user1, party);
        List<Rating> ratings1 = findAllComplimentsRatings(review1);
        review1.addAllRatings(ratings1);

        Review review2 = createReview(user3, user1, party);
        List<Rating> ratings2 = findAllImprovementsRatings(review2);
        review2.addAllRatings(ratings2);

        return new ArrayList<>(
                Arrays.asList(
                        review1,
                        review2
                )
        );
    }
}
