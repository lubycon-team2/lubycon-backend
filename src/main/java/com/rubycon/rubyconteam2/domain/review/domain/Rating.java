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

    @Column
    private int count;
}
