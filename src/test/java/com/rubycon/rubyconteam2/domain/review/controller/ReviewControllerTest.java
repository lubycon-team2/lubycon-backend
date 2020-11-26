package com.rubycon.rubyconteam2.domain.review.controller;

import com.rubycon.rubyconteam2.domain.party_join.domain.PartyJoin;
import com.rubycon.rubyconteam2.domain.review.dto.request.ReviewRequest;
import com.rubycon.rubyconteam2.domain.user.dto.response.ProfileReviewResponse;
import com.rubycon.rubyconteam2.domain.user.dto.response.ProfileWithRoleResponse;
import com.rubycon.rubyconteam2.global.common.WebMvcApiTest;
import com.rubycon.rubyconteam2.global.common.WithMockCustomUser;
import com.rubycon.rubyconteam2.global.factory.TestPartyJoinFactory;
import com.rubycon.rubyconteam2.global.factory.TestReviewFactory;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ReviewControllerTest extends WebMvcApiTest {

    @Test
    @WithMockCustomUser
    void findReviewableUsers() throws Exception {
        // Given
        List<PartyJoin> partyJoins = TestPartyJoinFactory.findAllPartyJoins();
        List<ProfileWithRoleResponse> responses = partyJoins.stream()
                .map(ProfileWithRoleResponse::new)
                .collect(Collectors.toList());

        given(partyJoinService.findAllReviewableUsers(1L, 1L))
                .willReturn(responses);

        int partyId = 1;
        // When & Then
        mockMvc.perform(get("/party/{partyId}/users/reviewable", partyId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @Test
    @WithMockCustomUser
    void review() throws Exception {
        // Given
        ReviewRequest reviewDto = TestReviewFactory.createReviewDto();

        int partyId = 1;
        int targetId = 2;
        // When & Then
        mockMvc.perform(post("/party/{partyId}/users/{targetId}/review", partyId, targetId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(reviewDto)))
                .andDo(print())
                .andExpect(status().isCreated())
        ;
    }
}