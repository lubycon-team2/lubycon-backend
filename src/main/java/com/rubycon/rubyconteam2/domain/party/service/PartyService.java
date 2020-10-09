package com.rubycon.rubyconteam2.domain.party.service;

import com.rubycon.rubyconteam2.domain.party.domain.*;
import com.rubycon.rubyconteam2.domain.party.dto.request.PartyCreateRequest;
import com.rubycon.rubyconteam2.domain.party.dto.request.PartyUpdateRequest;
import com.rubycon.rubyconteam2.domain.party.exception.*;
import com.rubycon.rubyconteam2.domain.party.repository.PartyJoinQueryRepository;
import com.rubycon.rubyconteam2.domain.party.repository.PartyJoinRepository;
import com.rubycon.rubyconteam2.domain.party.repository.PartyRepository;
import com.rubycon.rubyconteam2.domain.user.domain.User;
import com.rubycon.rubyconteam2.domain.user.exception.UserNotFoundException;
import com.rubycon.rubyconteam2.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.Part;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PartyService {

    private final UserRepository userRepository;
    private final PartyRepository partyRepository;
    private final PartyJoinRepository partyJoinRepository;
    private final PartyJoinQueryRepository partyJoinQueryRepository;

    /**
     * 서비스 타입에 따른 전체 모집 중 파티 검색
     */
    @Transactional
    public List<Party> findAll(ServiceType serviceType) {
        return partyRepository.findByServiceTypeIs(serviceType);
    }

    /**
     * 특정 파티 1개 조회
     */
    @Transactional
    public Party findById(Long partyId) {
        return partyRepository.findById(partyId)
                .orElseThrow(PartyNotFoundException::new);
    }

    /**
     * 모집 파티 생성 + 파티장 권한으로 가입
     * TODO : 파티는 각 서비스별 1개씩만 가입 되어있어야함!!
     */
    @Transactional
    public Party save(Long userId, PartyCreateRequest partyDto){
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        Party partyEntity = partyDto.toEntity();
        Party party = partyRepository.save(partyEntity);

        partyJoinRepository.save(PartyJoin.of(user, party));
        return party;
    }

    /**
     * 모집 파티 수정
     */
    @Transactional
    public Party update(Long partyId, PartyUpdateRequest partyDto){
        Party party = this.findById(partyId);
        party.updateMyParty(partyDto);

        return partyRepository.save(party);
    }

    /**
     * 모집 파티 삭제 + 파티장 권한을 가질 때만
     */
    @Transactional
    public void delete(Long userId, Long partyId){
        userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        Party party = this.findById(partyId);

        PartyState partyState = party.getPartyState();
        if (partyState.isEnd()) throw new PartyNotProceedingException();

        PartyJoin partyJoin = partyJoinQueryRepository.exists(userId, partyId)
                .orElseThrow(PartyJoinNotFoundException::new);

        Role role = partyJoin.getRole();
        if (role.isMember()) throw new PartyAccessDeniedException();

        party.setStateEnd();
    }
}
