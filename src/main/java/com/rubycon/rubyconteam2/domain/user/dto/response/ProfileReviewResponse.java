package com.rubycon.rubyconteam2.domain.user.dto.response;

import com.rubycon.rubyconteam2.domain.review.domain.Content;
import com.rubycon.rubyconteam2.domain.review.domain.ContentType;
import com.rubycon.rubyconteam2.domain.review.domain.Rating;
import com.rubycon.rubyconteam2.domain.review.domain.Review;
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
                        .reviews(group)
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

        private ContentType type;
        private Map<Content, Long> reviews;
    }
}
