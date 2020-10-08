package com.rubycon.rubyconteam2.domain.party.controller;

import com.rubycon.rubyconteam2.domain.party.domain.Party;
import com.rubycon.rubyconteam2.domain.party.domain.PartyState;
import com.rubycon.rubyconteam2.domain.party.domain.PaymentCycle;
import com.rubycon.rubyconteam2.domain.party.domain.ServiceType;
import com.rubycon.rubyconteam2.domain.party.dto.request.PartyCreateRequest;
import com.rubycon.rubyconteam2.domain.party.dto.request.PartyFindRequest;
import com.rubycon.rubyconteam2.domain.party.dto.request.PartyUpdateRequest;
import com.rubycon.rubyconteam2.domain.party.service.PartyService;
import com.rubycon.rubyconteam2.global.common.WebMvcApiTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PartyControllerTest extends WebMvcApiTest {

    @Test
    @WithMockUser // 모두 허용하기
    void findAllParty() throws Exception {
        // Given
        PartyFindRequest partyDto = PartyFindRequest.builder()
                .serviceType(ServiceType.NETFLIX.name())
                .build();

        List<Party> partyList = new ArrayList<>();

        // TODO : 더 좋은 방법 -> Factory 패턴 사용해서?
        Party party1 = new Party(1L, "넷플릭스 파티 모집", 3600, 3400, 0, PaymentCycle.MONTH_1, ServiceType.NETFLIX, PartyState.PROCEEDING );
        Party party2 = new Party(2L, "넷플릭스 파티 모집 - 2", 3600, 3400, 0, PaymentCycle.MONTH_3, ServiceType.NETFLIX, PartyState.PROCEEDING );
        Party party3 = new Party(3L, "넷플릭스 파티 모집 - 3", 3600, 3400, 0, PaymentCycle.YEAR_1, ServiceType.NETFLIX, PartyState.PROCEEDING );
        partyList.add(party1);
        partyList.add(party2);
        partyList.add(party3);

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

    // TODO : oauth2user 해결 될때 까지 보류 - 1
//    @Test
//    @WithMockUser
//    void saveParty() throws Exception {
//        // Given
//        PartyCreateRequest partyDto = PartyCreateRequest.builder()
//                .title("넷플릭스 파티 모집") // 반환 될 때 한글 깨짐
//                .leaderPrice(3400)
//                .memberPrice(3600)
//                .paymentCycle(PaymentCycle.MONTH_1.name())
//                .serviceType(ServiceType.NETFLIX.name())
//                .build();
//
//        Party party = partyDto.toEntity();
//        given(partyService.save(any(PartyCreateRequest.class)))
//                .willReturn(party);
//
//        // When & Then
//        mockMvc.perform(post("/party")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .characterEncoding("UTF-8")
//                .content(objectMapper.writeValueAsString(partyDto)))
//                .andDo(print())
//                .andExpect(status().isCreated())
//        ;
//    }

    @Test
    @WithMockUser
    void findParty() throws Exception {
        // Given
        int partyId = 1;

        Party party = new Party(1L, "넷플릭스 파티 모집 - Details", 3600, 3400, 0, PaymentCycle.MONTH_1, ServiceType.NETFLIX, PartyState.PROCEEDING );

        given(partyService.findById(any(Long.class)))
                .willReturn(party);

        // When & Then
        mockMvc.perform(get("/party/" + partyId)
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

        PartyUpdateRequest partyDto = PartyUpdateRequest.builder()
                .title("넷플릭스 파티 모집 - Update") // 반환 될 때 한글 깨짐
                .leaderPrice(3400)
                .memberPrice(3600)
                .paymentCycle(PaymentCycle.MONTH_1.name())
                .partyState(PartyState.PROCEEDING.name())
                .build();

        Party party = new Party(1L, "넷플릭스 파티 모집 - Update", 3600, 3400, 0, PaymentCycle.MONTH_1, ServiceType.NETFLIX, PartyState.PROCEEDING );
        given(partyService.update(any(Long.class), any(PartyUpdateRequest.class)))
                .willReturn(party);

        // When & Then
        mockMvc.perform(put("/party/" + partyId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(partyDto)))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    // TODO : oauth2user 해결 될때 까지 보류 - 2
//    @Test
//    @WithMockUser
//    void deleteParty() throws Exception {
//        // Given
//        int partyId = 1;
//
//        // When & Then
//        mockMvc.perform(delete("/party/" + partyId)
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .characterEncoding("UTF-8"))
//                .andDo(print())
//                .andExpect(status().isOk())
//        ;
//    }
}