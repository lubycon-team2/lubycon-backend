package com.rubycon.rubyconteam2.domain.review.repository;

import com.rubycon.rubyconteam2.domain.review.domain.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Long> {
}
