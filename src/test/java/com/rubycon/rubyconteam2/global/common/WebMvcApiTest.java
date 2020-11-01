package com.rubycon.rubyconteam2.global.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rubycon.rubyconteam2.domain.party.controller.PartyController;
import com.rubycon.rubyconteam2.domain.party_join.controller.PartyJoinController;
import com.rubycon.rubyconteam2.domain.party_join.service.PartyJoinService;
import com.rubycon.rubyconteam2.domain.party.service.PartyService;
import com.rubycon.rubyconteam2.global.config.SecurityTestConfig;
import com.rubycon.rubyconteam2.global.config.security.SecurityConfig;
import com.rubycon.rubyconteam2.global.config.security.filter.JwtAuthorizationFilter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(
        controllers = {
                PartyController.class,
                PartyJoinController.class
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

    @MockBean
    protected PartyService partyService;

    @MockBean
    protected PartyJoinService partyJoinService;
}