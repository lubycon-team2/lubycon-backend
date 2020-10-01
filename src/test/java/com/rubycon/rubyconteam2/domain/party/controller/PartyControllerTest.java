package com.rubycon.rubyconteam2.domain.party.controller;

import com.rubycon.rubyconteam2.domain.party.domain.Party;
import com.rubycon.rubyconteam2.domain.party.domain.PaymentCycle;
import com.rubycon.rubyconteam2.domain.party.domain.ServiceType;
import com.rubycon.rubyconteam2.domain.party.dto.request.PartyCreateRequest;
import com.rubycon.rubyconteam2.domain.party.service.PartyService;
import com.rubycon.rubyconteam2.global.common.WebMvcApiTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PartyControllerTest extends WebMvcApiTest {

    @MockBean
    PartyService partyService;

    @Test
    @WithMockUser
    void findAllParty() {
    }

    @Test
    @WithMockUser
    void saveParty() throws Exception {
        // Given
        PartyCreateRequest partyDto = PartyCreateRequest.builder()
                .title("넷플릭스 파티 모집") // 반환 될 때 한글 깨짐
                .leaderPrice(3400)
                .memberPrice(3600)
                .paymentCycle(PaymentCycle.MONTH_1.name())
                .serviceType(ServiceType.NETFLIX.name())
                .build();

        Party party = partyDto.toEntity();
        given(partyService.save(any(PartyCreateRequest.class)))
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
    void findParty() {
    }

    @Test
    void updateParty() {
    }

    @Test
    void deleteParty() {
    }
}