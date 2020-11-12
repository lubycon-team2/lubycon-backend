package com.rubycon.rubyconteam2.domain.party_join.controller;

import com.rubycon.rubyconteam2.domain.party.domain.*;
import com.rubycon.rubyconteam2.domain.party_join.domain.PartyJoin;
import com.rubycon.rubyconteam2.domain.party_join.domain.Role;
import com.rubycon.rubyconteam2.domain.user.domain.User;
import com.rubycon.rubyconteam2.global.common.WebMvcApiTest;
import com.rubycon.rubyconteam2.global.common.WithMockCustomUser;
import com.rubycon.rubyconteam2.global.factory.TestPartyFactory;
import com.rubycon.rubyconteam2.global.factory.TestPartyJoinFactory;
import com.rubycon.rubyconteam2.global.factory.TestUserFactory;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PartyJoinControllerTest extends WebMvcApiTest {

    @Test
    @WithMockCustomUser
    void joinParty() throws Exception {
        // Given
        User user = TestUserFactory.createUser(1L);
        Party party = TestPartyFactory.createParty(1L, ServiceType.NETFLIX);
        PartyJoin partyJoin = TestPartyJoinFactory.createPartyJoin(user, party, Role.MEMBER);

        given(partyJoinService.join(any(Long.class), any(Long.class)))
                .willReturn(partyJoin);

        int partyId = 1;
        // When & Then
        mockMvc.perform(post("/party/{partyId}/join", partyId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andDo(print())
                .andExpect(status().isCreated())
        ;
    }

    @Test
    @WithMockCustomUser
    void leaveParty() throws Exception {
        // Given
        int partyId = 1;

        // When & Then
        mockMvc.perform(delete("/party/{partyId}/leave", partyId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }
}