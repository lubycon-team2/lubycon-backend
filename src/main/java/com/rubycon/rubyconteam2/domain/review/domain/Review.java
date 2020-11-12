package com.rubycon.rubyconteam2.domain.review.domain;

import com.rubycon.rubyconteam2.domain.party.domain.Party;
import com.rubycon.rubyconteam2.domain.user.domain.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"reviewerId", "targetId", "partyId"})
})
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewerId")
    private User reviewer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "targetId")
    private User target;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partyId")
    private Party party;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rating> ratings;

    public static Review of(User reviewer, User target, Party party) {
        return Review.builder()
                .reviewer(reviewer)
                .target(target)
                .party(party)
                .ratings(new ArrayList<>())
                .build();
    }

    public void addAllRatings(List<Rating> ratings){
        ratings.forEach(rating -> rating.setReview(this));
        this.ratings = ratings;
    }

    public static Map<Content, Long> groupingByContent(List<Review> reviews){
        return reviews.stream()
                .map(Review::getRatings)
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(Rating::getContent, Collectors.counting()));
    }
}
