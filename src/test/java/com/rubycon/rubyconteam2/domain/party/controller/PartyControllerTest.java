package com.rubycon.rubyconteam2.domain.party.controller;

import com.rubycon.rubyconteam2.domain.party.domain.Party;
import com.rubycon.rubyconteam2.domain.party.domain.ServiceType;
import com.rubycon.rubyconteam2.domain.party.dto.request.PartyCreateRequest;
import com.rubycon.rubyconteam2.domain.party.dto.request.PartyFindRequest;
import com.rubycon.rubyconteam2.domain.party.dto.request.PartyUpdateRequest;
import com.rubycon.rubyconteam2.domain.party_join.domain.PartyJoin;
import com.rubycon.rubyconteam2.global.common.WebMvcApiTest;
import com.rubycon.rubyconteam2.global.common.WithMockCustomUser;
import com.rubycon.rubyconteam2.global.factory.TestPartyFactory;
import com.rubycon.rubyconteam2.global.factory.TestPartyJoinFactory;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PartyControllerTest extends WebMvcApiTest {

    @Test
    @WithMockUser    // 권한 필요 없는 곳은 없애도 동작하도록 !
    void findAllParty() throws Exception {
        // Given
        PartyFindRequest partyDto = TestPartyFactory.findPartyDto();

        List<Party> partyList = TestPartyFactory.findAllNetflixParties();

        given(partyService.findAll(any(ServiceType.class)))
                .willReturn(partyList);

        // When & Then
        mockMvc.perform(get("/party")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("serviceType", partyDto.getServiceType().name()))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @Test
    @WithMockCustomUser
    void saveParty() throws Exception {
        // Given
        PartyCreateRequest partyDto = TestPartyFactory.createPartyDto();

        Party party = partyDto.toEntity();
        given(partyService.save(any(Long.class), any(PartyCreateRequest.class)))
                .willReturn(party);

        // When & Then
        mockMvc.perform(post("/party")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(partyDto)))
                .andDo(print())
                .andExpect(status().isCreated())
        ;
    }

    @Test
    @WithMockUser
    void findPartyDetails() throws Exception {
        // Given
        int partyId = 1;

        List<PartyJoin> partyJoins = TestPartyJoinFactory.findAllPartyJoins();

        given(partyJoinService.findAllByPartyId(any(Long.class)))
                .willReturn(partyJoins);

        // When & Then
        mockMvc.perform(get("/party/{partyId}", partyId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @Test
    @WithMockUser
    void updateParty() throws Exception {
        // Given
        int partyId = 1;

        PartyUpdateRequest partyDto = TestPartyFactory.updatePartyDto();

        Party party = TestPartyFactory.createParty(1L, ServiceType.APPLE_MUSIC);
        party.updateMyParty(partyDto);

        given(partyService.update(any(Long.class), any(PartyUpdateRequest.class)))
                .willReturn(party);

        // When & Then
        mockMvc.perform(put("/party/{partyId}", partyId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(partyDto)))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @Test
    @WithMockCustomUser
    void deleteParty() throws Exception {
        // Given
        int partyId = 1;

        // When & Then
        mockMvc.perform(delete("/party/{partyId}", partyId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }
}