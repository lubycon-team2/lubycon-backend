package com.rubycon.rubyconteam2.domain.party.controller;

import com.rubycon.rubyconteam2.domain.party.domain.Party;
import com.rubycon.rubyconteam2.domain.party.domain.ServiceType;
import com.rubycon.rubyconteam2.domain.party.dto.request.PartyCreateRequest;
import com.rubycon.rubyconteam2.domain.party.dto.response.PartyListResponse;
import com.rubycon.rubyconteam2.domain.party.service.PartyService;
import com.rubycon.rubyconteam2.global.error.ErrorCodeType;
import com.rubycon.rubyconteam2.global.error.exception.NoContentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PartyController {

    @Autowired
    PartyService partyService;

    @PostMapping("/party")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveParty(@RequestBody PartyCreateRequest partyCreateRequest) {
        partyService.save(partyCreateRequest);
    }

    @GetMapping("/party")
    public List<PartyListResponse> findAllParty(@RequestParam("serviceType") ServiceType serviceType) {
        List<Party> partyList = partyService.findAll(serviceType);
        if (partyList.isEmpty()) throw new NoContentException();

        return partyList.stream()
                .map(PartyListResponse::new)
                .collect(Collectors.toList());
    }
}
