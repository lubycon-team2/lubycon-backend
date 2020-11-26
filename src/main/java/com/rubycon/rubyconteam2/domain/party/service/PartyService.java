package com.rubycon.rubyconteam2.domain.party.service;

import com.rubycon.rubyconteam2.domain.party.domain.*;
import com.rubycon.rubyconteam2.domain.party.dto.request.PartyCreateRequest;
import com.rubycon.rubyconteam2.domain.party.dto.request.PartyUpdateRequest;
import com.rubycon.rubyconteam2.domain.party.dto.response.PartyResponse;
import com.rubycon.rubyconteam2.domain.party.exception.*;
import com.rubycon.rubyconteam2.domain.party.repository.PartyQueryRepository;
import com.rubycon.rubyconteam2.domain.party_join.repository.PartyJoinQueryRepository;
import com.rubycon.rubyconteam2.domain.party_join.repository.PartyJoinRepository;
import com.rubycon.rubyconteam2.domain.party.repository.PartyRepository;
import com.rubycon.rubyconteam2.domain.party_join.domain.PartyJoin;
import com.rubycon.rubyconteam2.domain.party_join.domain.Role;
import com.rubycon.rubyconteam2.domain.party_join.exception.PartyJoinNotFoundException;
import com.rubycon.rubyconteam2.domain.user.domain.User;
import com.rubycon.rubyconteam2.domain.user.exception.UserNotFoundException;
import com.rubycon.rubyconteam2.domain.user.repository.UserRepository;
import com.rubycon.rubyconteam2.global.common.page.PageRequest;
import com.rubycon.rubyconteam2.global.error.exception.NoContentException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PartyService {

    private final UserRepository userRepository;
    private final PartyRepository partyRepository;
    private final PartyQueryRepository partyQueryRepository;
    private final PartyJoinRepository partyJoinRepository;
    private final PartyJoinQueryRepository partyJoinQueryRepository;

    /**
     * 서비스 타입에 따른 전체 모집 중 파티 검색
     */
    @Transactional(readOnly = true)
    public Page<PartyResponse> findAll(ServiceType serviceType, PageRequest pageRequest) {
        Page<Party> partyList = partyQueryRepository.findByServiceTypeIs(serviceType, pageRequest.of());
        if (partyList.getContent().isEmpty()) throw new NoContentException();

        return partyList.map(PartyResponse::new);
    }

    /**
     * 모집 파티 생성 + 파티장 권한으로 가입
     * TODO : 파티는 각 서비스별 1개씩만 가입 되어있어야함!!
     * user id만으로 save?
     */
    @Transactional
    public PartyResponse save(Long userId, PartyCreateRequest partyDto){
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        Party partyEntity = partyDto.toEntity();
        Party party = partyRepository.save(partyEntity);

        partyJoinRepository.save(PartyJoin.of(user, party, Role.LEADER));
        return new PartyResponse(party);
    }

    /**
     * 모집 파티 수정
     */
    @Transactional
    public PartyResponse update(Long partyId, PartyUpdateRequest partyDto){
        Party party = partyRepository.findById(partyId)
                .orElseThrow(PartyNotFoundException::new);
        party.updateMyParty(partyDto);

        return new PartyResponse(party);
    }

    /**
     * 모집 파티 삭제 + 파티장 권한을 가질 때만
     */
    @Transactional
    public void delete(Long userId, Long partyId){
        userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        Party party = partyRepository.findById(partyId)
                .orElseThrow(PartyNotFoundException::new);

        PartyState partyState = party.getPartyState();
        if (partyState.isDeleted()) throw new PartyNotProceedingException();

        PartyJoin partyJoin = partyJoinQueryRepository.exists(userId, partyId)
                .orElseThrow(PartyJoinNotFoundException::new);

        Role role = partyJoin.getRole();
        if (role.isMember()) throw new PartyAccessDeniedException();

        party.setStateDeleted();
    }
}
