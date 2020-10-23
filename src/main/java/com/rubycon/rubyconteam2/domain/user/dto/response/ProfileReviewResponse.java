package com.rubycon.rubyconteam2.domain.user.dto.response;

import com.rubycon.rubyconteam2.domain.review.domain.Content;
import com.rubycon.rubyconteam2.domain.review.domain.ContentType;
import com.rubycon.rubyconteam2.domain.review.domain.Rating;
import com.rubycon.rubyconteam2.domain.review.domain.Review;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileReviewResponse {

    private List<ReviewGroupByType> reviews;

    public ProfileReviewResponse(List<Review> reviews) {
        Map<Content, Long> groups = reviews.stream()
                .map(Review::getRating)
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(Rating::getContent, Collectors.counting()));

        this.reviews = new ArrayList<>();

        for (ContentType type : ContentType.values()){
            Map<Content, Long> group = groups.entrySet().stream()
                    .filter(e -> e.getKey().getContentType().equals(type))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

            this.reviews.add(
                    ReviewGroupByType.builder()
                        .type(type)
                        .review(group)
                        .build()
            );
        }
//        Map<ContentType, List<Map.Entry<Content, Long>>> response = groups.entrySet().stream()
//                .collect(Collectors.groupingBy(o-> o.getKey().getContentType()));
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
    }
}
