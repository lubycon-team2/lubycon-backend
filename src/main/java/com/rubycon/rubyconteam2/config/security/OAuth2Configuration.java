package com.rubycon.rubyconteam2.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

import java.util.Collections;

@Configuration
public class OAuth2Configuration {

    @Bean
    public OAuth2AuthorizedClientService authorizedClientService() {
        return new CustomOAuth2AuthorizedClientService();
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        final ClientRegistration clientRegistration = CustomOAuthProvider.KAKAO
                .getBuilder()
                .build();

        return new InMemoryClientRegistrationRepository(Collections.singletonList(
                clientRegistration
        ));
    }

}
