package com.rubycon.rubyconteam2.domain.party.controller;

import com.rubycon.rubyconteam2.domain.party.domain.Party;
import com.rubycon.rubyconteam2.domain.party.domain.PartyJoin;
import com.rubycon.rubyconteam2.domain.party.domain.PaymentCycle;
import com.rubycon.rubyconteam2.domain.party.domain.ServiceType;
import com.rubycon.rubyconteam2.domain.party.dto.request.PartyCreateRequest;
import com.rubycon.rubyconteam2.domain.party.service.PartyJoinService;
import com.rubycon.rubyconteam2.domain.party.service.PartyService;
import com.rubycon.rubyconteam2.domain.user.domain.User;
import com.rubycon.rubyconteam2.global.common.WebMvcApiTest;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PartyJoinControllerTest extends WebMvcApiTest {

    @MockBean
    private PartyService partyService;

    @MockBean
    private PartyJoinService partyJoinService;

//    @Test
//    @WithMockUser
//    void joinParty() throws Exception {
//        // Given
//        User user = User.builder()
//                .userId(1L)
//                .build();
//        Party party = Party.builder()
//                .partyId(1L)
//                .build();
//        PartyJoin partyJoin = PartyJoin.builder()
//                .user(user)
//                .party(party)
//                .build();
//        given(partyJoinService.join(any(Long.class), any(Long.class)))
//                .willReturn(partyJoin);
//
//        int partyId = 1;
//        // When & Then
//        mockMvc.perform(post("/party/" + partyId + "/join")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .characterEncoding("UTF-8"))
//                .andDo(print())
//                .andExpect(status().isCreated())
//        ;
//    }

    @Test
    @WithMockUser
    void leaveParty() throws Exception {
    }
}