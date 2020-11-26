package com.rubycon.rubyconteam2.domain.user.dto.response;

import com.rubycon.rubyconteam2.domain.review.domain.Content;
import com.rubycon.rubyconteam2.domain.review.domain.ContentType;
import com.rubycon.rubyconteam2.domain.review.domain.Review;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileReviewResponse {

    private List<ReviewGroupByType> reviews = new ArrayList<>();

    public ProfileReviewResponse(List<Review> reviews) {
        Map<Content, Long> groups = Review.groupingByContent(reviews);

        for (ContentType type : ContentType.values()) {
            ReviewGroupByType review = ReviewGroupByType.of(type, groups);
            this.reviews.add(review);
        }
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class ReviewGroupByType {

        @ApiModelProperty(value = "리뷰 타입", example = "COMPLIMENTS | IMPROVEMENTS")
        private ContentType type;

        @ApiModelProperty(
                value = "리뷰 내용 및 개수",
                example = "{ 'IMPROVEMENTS_1' : 1, 'IMPROVEMENTS_2' : 2 }",
                dataType = "Map[Content, Long]",
                reference = "Map"
        )
        private Map<Content, Long> review;

        public static ReviewGroupByType of(ContentType type, Map<Content, Long> groups) {
            Map<Content, Long> reviewWithCount = groups.entrySet().stream()
                    .filter(e -> e.getKey().getContentType().equals(type))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

            return ReviewGroupByType.builder()
                    .type(type)
                    .review(reviewWithCount)
                    .build();
        }
    }
}
