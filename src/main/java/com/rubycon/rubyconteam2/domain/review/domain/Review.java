package com.rubycon.rubyconteam2.domain.review.domain;

import com.rubycon.rubyconteam2.domain.user.domain.User;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"reviewerId", "targetId"})
})
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @ManyToOne
    @JoinColumn(name = "reviewerId")
    private User reviewer;

    @ManyToOne
    @JoinColumn(name = "targetId")
    private User target;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rating> rating;

    public static Review of(User reviewer, User target, List<Rating> rating) {
        return Review.builder()
                .reviewer(reviewer)
                .target(target)
                .rating(rating)
                .build();
    }
}
