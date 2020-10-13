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

    @OneToMany
    @JoinColumn(name = "reviewId")
    private List<Rating> rating;
}
