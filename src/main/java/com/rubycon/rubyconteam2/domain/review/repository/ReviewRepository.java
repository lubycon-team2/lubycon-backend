package com.rubycon.rubyconteam2.domain.review.repository;

import com.rubycon.rubyconteam2.domain.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
