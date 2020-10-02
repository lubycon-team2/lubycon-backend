package com.rubycon.rubyconteam2.global.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rubycon.rubyconteam2.domain.party.controller.PartyController;
import com.rubycon.rubyconteam2.global.config.SecurityTestConfig;
import com.rubycon.rubyconteam2.global.config.oauth.handler.OAuth2SuccessHandler;
import com.rubycon.rubyconteam2.global.config.security.SecurityConfig;
import com.rubycon.rubyconteam2.global.config.security.entrypoint.JwtAuthenticationEntryPoint;
import com.rubycon.rubyconteam2.global.config.security.filter.ExceptionHandlerFilter;
import com.rubycon.rubyconteam2.global.config.security.filter.JwtAuthorizationFilter;
import com.rubycon.rubyconteam2.global.core.jwt.controller.JwtTestController;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(
        controllers = {
                PartyController.class,
        },
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class),
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthorizationFilter.class), // Why ?!
        }
)
@Import(SecurityTestConfig.class)
public abstract class WebMvcApiTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected ModelMapper modelMapper;
}