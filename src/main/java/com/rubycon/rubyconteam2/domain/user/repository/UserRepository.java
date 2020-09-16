package com.rubycon.rubyconteam2.domain.user.repository;

import com.rubycon.rubyconteam2.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByOauthKey(String oauthKey);
}
