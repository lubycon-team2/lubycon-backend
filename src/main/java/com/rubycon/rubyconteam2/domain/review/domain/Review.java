package com.rubycon.rubyconteam2.domain.review.domain;

import com.rubycon.rubyconteam2.domain.party.domain.Party;
import com.rubycon.rubyconteam2.domain.user.domain.User;
import lombok.*;

import javax.persistence.*;
import java.util.List;

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
    private List<Rating> rating;

    public static Review of(User reviewer, User target, Party party, List<Rating> rating) {
        return Review.builder()
                .reviewer(reviewer)
                .target(target)
                .party(party)
                .rating(rating)
                .build();
    }
}
