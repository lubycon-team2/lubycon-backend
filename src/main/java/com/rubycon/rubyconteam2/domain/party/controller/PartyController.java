package com.rubycon.rubyconteam2.domain.party.controller;

import com.rubycon.rubyconteam2.domain.party.domain.Party;
import com.rubycon.rubyconteam2.domain.party.dto.request.PartyCreateRequest;
import com.rubycon.rubyconteam2.domain.party.dto.request.PartyFindRequest;
import com.rubycon.rubyconteam2.domain.party.dto.request.PartyUpdateRequest;
import com.rubycon.rubyconteam2.domain.party.dto.response.PartyResponse;
import com.rubycon.rubyconteam2.domain.party.service.PartyService;
import com.rubycon.rubyconteam2.global.error.exception.NoContentException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/party")
@RequiredArgsConstructor
@Validated
public class PartyController {

    private final PartyService partyService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PartyResponse> findAllParty(
            @RequestParam("serviceType") @Valid PartyFindRequest partyDto
    ) {
        System.out.println(partyDto);

        List<Party> partyList = partyService.findAll(partyDto.getServiceType());
        if (partyList.isEmpty()) throw new NoContentException();

        return partyList.stream()
                .map(PartyResponse::new)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PartyResponse saveParty(
            @RequestBody @Valid PartyCreateRequest partyDto
    ) {
        Party party = partyService.save(partyDto);
        return new PartyResponse(party);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PartyResponse updateParty(
            @PathVariable final Long id,
            @RequestBody @Valid PartyUpdateRequest partyDto
    ){
        Party party = partyService.update(id, partyDto);
        return new PartyResponse(party);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteParty(
            @PathVariable final Long id
    ){
        partyService.delete(id);
    }
}
