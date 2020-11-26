package com.rubycon.rubyconteam2.domain.user.controller;

import com.rubycon.rubyconteam2.domain.review.domain.Review;
import com.rubycon.rubyconteam2.domain.user.domain.User;
import com.rubycon.rubyconteam2.domain.user.dto.response.ProfileResponse;
import com.rubycon.rubyconteam2.domain.user.dto.response.ProfileReviewResponse;
import com.rubycon.rubyconteam2.global.common.WebMvcApiTest;
import com.rubycon.rubyconteam2.global.common.WithMockCustomUser;
import com.rubycon.rubyconteam2.global.factory.TestReviewFactory;
import com.rubycon.rubyconteam2.global.factory.TestUserFactory;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProfileControllerTest extends WebMvcApiTest {

    @Test
    @WithMockCustomUser
    void me() throws Exception {
        // Given
        User user = TestUserFactory.createUser(1L);
        ProfileResponse response = new ProfileResponse(user);

        given(userService.findById(1L))
                .willReturn(response);

        // When & Then
        mockMvc.perform(get("/profiles/me")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @Test
    void findAllReview() throws Exception {
        // Given
        List<Review> reviews = TestReviewFactory.findAllReviews();
        ProfileReviewResponse response = new ProfileReviewResponse(reviews);

        given(reviewService.findAllReview(1L))
                .willReturn(response);

        int userId = 1;
        // When & Then
        mockMvc.perform(get("/profiles/{userId}/reviews", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }
}