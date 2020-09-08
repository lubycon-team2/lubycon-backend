package com.rubycon.rubyconteam2.domain.party.controller;

import com.rubycon.rubyconteam2.domain.party.domain.Party;
import com.rubycon.rubyconteam2.domain.party.dto.request.PartyCreateRequest;
import com.rubycon.rubyconteam2.domain.party.service.PartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PartyController {

    @Autowired
    PartyService partyService;

    @PostMapping("/party")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveParty(@RequestBody PartyCreateRequest partyCreateRequest) {
        partyService.save(partyCreateRequest);
    }

//    @GetMapping("/party")
//    public List<Party> findAllParty() {
//        List<Party> partyList = partyService.findAll();
//        return partyList.stream().map()
//    }
}
