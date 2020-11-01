package com.rubycon.rubyconteam2.domain.party_join.repository;

import com.rubycon.rubyconteam2.domain.party_join.domain.PartyJoin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartyJoinRepository extends JpaRepository<PartyJoin, Long> {
}
