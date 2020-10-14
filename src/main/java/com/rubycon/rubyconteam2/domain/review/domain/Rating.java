package com.rubycon.rubyconteam2.domain.review.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ratingId;

    @Column(nullable = false, length = 45)
    @Enumerated(EnumType.STRING)
    private Content content;

    /**
     * Rating과 Review를 양방향 맺은 이유
     * 단방향으로 맺으면 save시 update 되는 이슈 때문
     */
    @ManyToOne
    @JoinColumn(name = "reviewId")
    private Review review;

    public static Rating of(Content content, Review review){
        return Rating.builder()
                .content(content)
                .review(review)
                .build();
    }
}
