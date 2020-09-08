package com.rubycon.rubyconteam2.domain.party.service;

import com.rubycon.rubyconteam2.domain.party.domain.Party;
import com.rubycon.rubyconteam2.domain.party.domain.ServiceType;
import com.rubycon.rubyconteam2.domain.party.dto.request.PartyCreateRequest;
import com.rubycon.rubyconteam2.domain.party.repository.PartyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PartyService {
    @Autowired
    PartyRepository partyRepository;

    /**
     * 모집 중인 전체 파티 검색
     */
    public Long save(PartyCreateRequest partyCreateRequest){
        Party party = partyCreateRequest.toEntity();
        log.debug(party.toString());
        return partyRepository.save(party).getPartyId();
    }

    public List<Party> findAll(ServiceType serviceType) {
        return partyRepository.findByServiceTypeIs(serviceType);
    }
}
