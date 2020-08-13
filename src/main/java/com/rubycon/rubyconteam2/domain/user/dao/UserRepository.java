package com.rubycon.rubyconteam2.domain.user.dao;

import com.rubycon.rubyconteam2.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
