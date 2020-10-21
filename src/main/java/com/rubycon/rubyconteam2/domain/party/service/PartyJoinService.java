package com.rubycon.rubyconteam2.domain.party.service;

import com.rubycon.rubyconteam2.domain.party.domain.*;
import com.rubycon.rubyconteam2.domain.party.exception.*;
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

import java.util.List;
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
     * + 서비스 별 1개 파티만 참여 가능하도록 (최대 4개 파티, 종료된 파티 제외)
     * + 파티 최대 인원 수를 초과했으면 참가 X
     */
    @Transactional
    public PartyJoin join(Long userId, Long partyId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        Party party = partyRepository.findById(partyId)
                .orElseThrow(PartyNotFoundException::new);

        PartyState partyState = party.getPartyState();
        if (partyState.isEnd()) throw new PartyNotProceedingException();

        ServiceType service = party.getServiceType();
        if (service.isOverMemberCount(party)) throw new PartyOverMaxCountException();

        // 진행 중이고 동일한 서비스에 가입했는지 검사
        partyJoinQueryRepository
                .findByUserIdAndTypeAndState(userId, service, partyState)
                .ifPresent(partyJoin -> {
                    throw new PartyAlreadyJoinException();
                });

        Optional<PartyJoin> optional = partyJoinQueryRepository.exists(userId, partyId);
        if (!optional.isPresent()) {
            party.plusMemberCount();
            return partyJoinRepository.save(PartyJoin.of(user, party, Role.MEMBER));
        }

        PartyJoin partyJoin = optional.get();

        party.plusMemberCount();
        partyJoin.setIsDeleted(Boolean.FALSE);
        return partyJoin;
    }

    /**
     * 특정 사용자가 특정 파티에 탈퇴하기
     * + 파티가 진행 중인 상태에만 & 파티원만!
     */
    @Transactional
    public void leave(Long userId, Long partyId) {
        userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        Party party = partyRepository.findById(partyId)
                .orElseThrow(PartyNotFoundException::new);

        PartyState partyState = party.getPartyState();
        if (partyState.isEnd()) throw new PartyNotProceedingException();

        PartyJoin partyJoin = partyJoinQueryRepository.exists(userId, partyId)
                .orElseThrow(PartyJoinNotFoundException::new);

        Role role = partyJoin.getRole();
        if (role.isLeader()) throw new PartyAccessDeniedException();

        if (partyJoin.isDeleted()) throw new PartyAlreadyLeaveException();

        party.minusMemberCount();
        partyJoin.setIsDeleted(Boolean.TRUE);
    }

    /**
     * 특정 사용자가 가입한 파티 조회 ( 파티 상태 별 )
     * TODO : 메서드 위치가 이곳이 맞는지?
     */
    @Transactional
    public List<PartyJoin> findAllMyPartyByState(Long userId, PartyState partyState) {
        userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        return partyJoinQueryRepository.findAllMyPartyByState(userId, partyState);
    }

    @Transactional
    public List<PartyJoin> findAllByPartyId(Long partyId) {
        partyRepository.findById(partyId)
                .orElseThrow(PartyNotFoundException::new);

        return partyJoinQueryRepository.findAllByPartyId(partyId);
    }
}
