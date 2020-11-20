package com.rubycon.rubyconteam2.global.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rubycon.rubyconteam2.domain.party.controller.PartyController;
import com.rubycon.rubyconteam2.domain.party_join.controller.PartyJoinController;
import com.rubycon.rubyconteam2.domain.party_join.service.PartyJoinService;
import com.rubycon.rubyconteam2.domain.party.service.PartyService;
import com.rubycon.rubyconteam2.domain.review.controller.ReviewController;
import com.rubycon.rubyconteam2.domain.review.service.ReviewService;
import com.rubycon.rubyconteam2.domain.user.controller.ProfileController;
import com.rubycon.rubyconteam2.domain.user.service.UserService;
import com.rubycon.rubyconteam2.global.config.SecurityTestConfig;
import com.rubycon.rubyconteam2.global.config.security.SecurityConfig;
import com.rubycon.rubyconteam2.global.config.security.filter.JwtAuthorizationFilter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@WebMvcTest(
        controllers = {
                PartyController.class,
                PartyJoinController.class,
                ProfileController.class,
                ReviewController.class
        },
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class),
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthorizationFilter.class), // Why ?!
        }
)
@Import(SecurityTestConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class WebMvcApiTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext ctx;

    @BeforeAll
    public void init() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))  // 필터 추가
                .build();
    }


    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected ModelMapper modelMapper;

    @MockBean
    protected PartyService partyService;

    @MockBean
    protected PartyJoinService partyJoinService;

    @MockBean
    protected ReviewService reviewService;

    @MockBean
    protected UserService userService;
}