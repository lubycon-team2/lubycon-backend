package com.rubycon.rubyconteam2.domain.party.service;

import com.rubycon.rubyconteam2.domain.party.domain.Party;
import com.rubycon.rubyconteam2.domain.party.domain.PartyJoin;
import com.rubycon.rubyconteam2.domain.party.domain.Role;
import com.rubycon.rubyconteam2.domain.party.domain.ServiceType;
import com.rubycon.rubyconteam2.domain.party.exception.PartyNotFoundException;
import com.rubycon.rubyconteam2.domain.party.repository.PartyJoinRepository;
import com.rubycon.rubyconteam2.domain.party.repository.PartyRepository;
import com.rubycon.rubyconteam2.domain.user.domain.User;
import com.rubycon.rubyconteam2.domain.user.exception.UserNotFoundException;
import com.rubycon.rubyconteam2.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PartyJoinService {

    private final UserRepository userRepository;
    private final PartyRepository partyRepository;
    private final PartyJoinRepository partyJoinRepository;

    /**
     * 특정 사용자가 특정 파티에 참여하기
     */
    public PartyJoin join(Long userId, Long partyId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        Party party = partyRepository.findById(partyId)
                .orElseThrow(PartyNotFoundException::new);

        // TODO : QueryDSL 추가 후 exists 메서드 만들기
//        PartyJoin partyJoin = partyJoinRepository.exists(postId, userId);
        PartyJoin partyJoin = PartyJoin.builder()
                .user(user)
                .party(party)
                .role(Role.MEMBER)
                .build();
        return partyJoinRepository.save(partyJoin);
    }

    /**
     * 특정 사용자가 특정 파티에 탈퇴하기
     */
    public void leave(Long userId, Long partyId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        Party party = partyRepository.findById(partyId)
                .orElseThrow(PartyNotFoundException::new);

        // TODO : QueryDSL 추가 후 exists 메서드 만들기
//        PartyJoin partyJoin = partyJoinRepository.exists(postId, userId);
        PartyJoin partyJoin = PartyJoin.builder()
                .user(user)
                .party(party)
                .role(Role.MEMBER)
                .build();
        partyJoinRepository.delete(partyJoin);
    }
}
