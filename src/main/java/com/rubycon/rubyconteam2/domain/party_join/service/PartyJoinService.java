package com.rubycon.rubyconteam2.domain.party_join.service;

import com.rubycon.rubyconteam2.domain.party.domain.*;
import com.rubycon.rubyconteam2.domain.party.exception.*;
import com.rubycon.rubyconteam2.domain.party_join.repository.PartyJoinQueryRepository;
import com.rubycon.rubyconteam2.domain.party_join.repository.PartyJoinRepository;
import com.rubycon.rubyconteam2.domain.party.repository.PartyRepository;
import com.rubycon.rubyconteam2.domain.party_join.domain.PartyJoin;
import com.rubycon.rubyconteam2.domain.party_join.domain.Role;
import com.rubycon.rubyconteam2.domain.party_join.exception.PartyAlreadyJoinException;
import com.rubycon.rubyconteam2.domain.party_join.exception.PartyAlreadyLeaveException;
import com.rubycon.rubyconteam2.domain.party_join.exception.PartyJoinNotFoundException;
import com.rubycon.rubyconteam2.domain.user.domain.User;
import com.rubycon.rubyconteam2.domain.user.exception.UserNotFoundException;
import com.rubycon.rubyconteam2.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        if (partyState.isDeleted()) throw new PartyNotProceedingException();

        ServiceType service = party.getServiceType();
        if (service.isOverMemberCount(party)) throw new PartyOverMaxCountException();
        if (service.isRecruitingCompleted(party)) party.setStateCompleted();

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
        partyJoin.setJoinDate(LocalDateTime.now());
        partyJoin.setLeaveDate(null);
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
        if (partyState.isDeleted()) throw new PartyNotProceedingException();
        if (partyState.isCompleted()) party.setStateAdditionalRecruiting();

        PartyJoin partyJoin = partyJoinQueryRepository.exists(userId, partyId)
                .orElseThrow(PartyJoinNotFoundException::new);

        Role role = partyJoin.getRole();
        if (role.isLeader()) throw new PartyAccessDeniedException();

        if (partyJoin.isDeleted()) throw new PartyAlreadyLeaveException();

        party.minusMemberCount();
        partyJoin.setIsDeleted(Boolean.TRUE);
        partyJoin.setLeaveDate(LocalDateTime.now());
    }

    /**
     * 특정 사용자 파티에서 강퇴하기
     * + 파티장만 파티원 강퇴 가능
     * (우선 강퇴 당해도 다시 들어올 수 있음)
     */
    @Transactional
    public void kickOff(Long userId, Long targetId, Long partyId) {
        userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        userRepository.findById(targetId)
                .orElseThrow(UserNotFoundException::new);
        Party party = partyRepository.findById(partyId)
                .orElseThrow(PartyNotFoundException::new);

        PartyState partyState = party.getPartyState();
        if (partyState.isDeleted()) throw new PartyNotProceedingException();
        if (partyState.isCompleted()) party.setStateAdditionalRecruiting();

        PartyJoin myPartyJoin = partyJoinQueryRepository.exists(userId, partyId)
                .orElseThrow(PartyJoinNotFoundException::new);

        Role role = myPartyJoin.getRole();
        if (role.isMember()) throw new PartyAccessDeniedException();

        PartyJoin targetPartyJoin = partyJoinQueryRepository.exists(targetId, partyId)
                .orElseThrow(PartyJoinNotFoundException::new);

        party.minusMemberCount();
        targetPartyJoin.setIsDeleted(Boolean.TRUE);
        targetPartyJoin.setLeaveDate(LocalDateTime.now());
    }

    /**
     * 특정 사용자가 가입한 파티 조회 ( 파티 상태 별 )
     */
    @Transactional
    public List<PartyJoin> findAllMyPartyByState(Long userId, PartyState partyState) {
        userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        return partyJoinQueryRepository.findAllMyPartyByState(userId, partyState);
    }

    /**
     * 파티 상세 조회 (파티 정보 + 가입한 유저 정보)
     */
    @Transactional
    public List<PartyJoin> findAllByPartyId(Long partyId) {
        partyRepository.findById(partyId)
                .orElseThrow(PartyNotFoundException::new);

        List<PartyJoin> partyJoins = partyJoinQueryRepository.findAllByPartyId(partyId);
        return partyJoins.stream()
                .filter(PartyJoin::isPresent)
                .collect(Collectors.toList());
    }

    /**
     * 현재 내가 리뷰 가능한 사용자 리스트 조회
     */
    @Transactional
    public List<PartyJoin> findAllReviewableUsers(Long userId, Long partyId){
        userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        partyRepository.findById(partyId)
                .orElseThrow(PartyNotFoundException::new);

        List<PartyJoin> partyJoins = partyJoinQueryRepository.findAllByPartyId(partyId);
        PartyJoin myPartyJoin = partyJoins.stream()
                .filter(partyJoin -> partyJoin.isEquals(userId))
                .findFirst()
                .orElseThrow(PartyJoinNotFoundException::new);

        return partyJoins.stream()
                .filter(partyJoin -> partyJoin.isNotEquals(userId))
                .filter(partyJoin -> partyJoin.isReviewable(myPartyJoin))
                .collect(Collectors.toList());
    }
}
