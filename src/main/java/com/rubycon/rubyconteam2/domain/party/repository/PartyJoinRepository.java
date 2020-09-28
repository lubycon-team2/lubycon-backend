package com.rubycon.rubyconteam2.domain.party.repository;

import com.rubycon.rubyconteam2.domain.party.domain.PartyJoin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartyJoinRepository extends JpaRepository<PartyJoin, Long> {
}
