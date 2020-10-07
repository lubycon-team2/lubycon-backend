package com.rubycon.rubyconteam2.domain.party.service;

import com.rubycon.rubyconteam2.domain.party.domain.Party;
import com.rubycon.rubyconteam2.domain.party.domain.PartyJoin;
import com.rubycon.rubyconteam2.domain.party.exception.PartyJoinDuplicatedException;
import com.rubycon.rubyconteam2.domain.party.exception.PartyJoinNotFoundException;
import com.rubycon.rubyconteam2.domain.party.exception.PartyNotFoundException;
import com.rubycon.rubyconteam2.domain.party.repository.PartyJoinQueryRepository;
import com.rubycon.rubyconteam2.domain.party.repository.PartyJoinRepository;
import com.rubycon.rubyconteam2.domain.party.repository.PartyRepository;
import com.rubycon.rubyconteam2.domain.user.domain.User;
import com.rubycon.rubyconteam2.domain.user.exception.UserNotFoundException;
import com.rubycon.rubyconteam2.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PartyJoinService {

    private final UserRepository userRepository;
    private final PartyRepository partyRepository;
    private final PartyJoinRepository partyJoinRepository;
    private final PartyJoinQueryRepository partyJoinQueryRepository;

    /**
     * 특정 사용자가 특정 파티에 참여하기
     * TODO : 서비스 별 1개 파티만 참여 가능하도록 (최대 4개 파티, 종료된 파티 제외)
     */
    @Transactional
    public PartyJoin join(Long userId, Long partyId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        Party party = partyRepository.findById(partyId)
                .orElseThrow(PartyNotFoundException::new);

        Optional<PartyJoin> optional = partyJoinQueryRepository.exists(userId, partyId);
        if (optional.isPresent()) throw new PartyJoinDuplicatedException();

        party.plusMemberCount();
        return partyJoinRepository.save(PartyJoin.of(user, party));
    }

    /**
     * 특정 사용자가 특정 파티에 탈퇴하기
     * TODO : 파티가 진행 중인 상태에만 !
     */
    @Transactional
    public void leave(Long userId, Long partyId) {
        userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        Party party = partyRepository.findById(partyId)
                .orElseThrow(PartyNotFoundException::new);

        PartyJoin partyJoin = partyJoinQueryRepository.exists(userId, partyId)
                .orElseThrow(PartyJoinNotFoundException::new);

        party.minusMemberCount();
        partyJoinRepository.delete(partyJoin);
    }
}