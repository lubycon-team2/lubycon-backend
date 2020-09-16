package com.rubycon.rubyconteam2.domain.party.controller;

import com.rubycon.rubyconteam2.domain.party.domain.Party;
import com.rubycon.rubyconteam2.domain.party.domain.ServiceType;
import com.rubycon.rubyconteam2.domain.party.dto.request.PartyCreateRequest;
import com.rubycon.rubyconteam2.domain.party.dto.response.PartyResponse;
import com.rubycon.rubyconteam2.domain.party.service.PartyService;
import com.rubycon.rubyconteam2.global.error.exception.NoContentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/party")
public class PartyController {

    @Autowired
    PartyService partyService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PartyResponse saveParty(
            @RequestBody @Valid PartyCreateRequest partyCreateRequest
    ) {
        Party party = partyService.save(partyCreateRequest);
        return new PartyResponse(party);
    }

    @GetMapping
    public List<PartyResponse> findAllParty(
            @RequestParam("serviceType") @Valid @NotEmpty ServiceType serviceType
    ) {
        List<Party> partyList = partyService.findAll(serviceType);
        if (partyList.isEmpty()) throw new NoContentException();

        return partyList.stream()
                .map(PartyResponse::new)
                .collect(Collectors.toList());
    }
}
