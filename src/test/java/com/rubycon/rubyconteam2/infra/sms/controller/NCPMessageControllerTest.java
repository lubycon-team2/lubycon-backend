package com.rubycon.rubyconteam2.infra.sms.controller;

import com.rubycon.rubyconteam2.global.common.WebMvcApiTest;
import com.rubycon.rubyconteam2.global.common.WithMockCustomUser;
import com.rubycon.rubyconteam2.infra.sms.dto.request.NCPSendRequest;
import com.rubycon.rubyconteam2.infra.sms.dto.request.NCPVerifyRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class NCPMessageControllerTest extends WebMvcApiTest {

    @Test
    void sendSMS() throws Exception {
        // Given
        NCPSendRequest sendDto = NCPSendRequest.builder()
                .to("01012341234")
                .build();

        // When & Then
        mockMvc.perform(post("/authenticate/sms/send")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(sendDto)))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @Test
    @WithMockCustomUser
    void verify() throws Exception {
        // Given
        NCPVerifyRequest verifyDto = NCPVerifyRequest.builder()
                .to("01012341234")
                .code("123456")
                .build();

        // When & Then
        mockMvc.perform(post("/authenticate/sms/verify")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(verifyDto)))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }
}