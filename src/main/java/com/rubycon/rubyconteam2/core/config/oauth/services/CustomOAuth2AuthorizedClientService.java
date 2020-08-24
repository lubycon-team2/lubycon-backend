package com.rubycon.rubyconteam2.core.config.oauth.services;

import com.rubycon.rubyconteam2.domain.user.dao.UserRepository;
import com.rubycon.rubyconteam2.domain.user.domain.Role;
import com.rubycon.rubyconteam2.domain.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

/*
 *   OAuth2AuthorizedClientService : 인증 정보 저장을 위한 표준 인터페이스
 *   vs DefaultOAuth2UserService와의 차이점?
 *
 *   직접 생성한 구현 클래스 - 인증 정보를 DB에 저장
 */
@Service
public class CustomOAuth2AuthorizedClientService implements OAuth2AuthorizedClientService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void saveAuthorizedClient(OAuth2AuthorizedClient oAuth2AuthorizedClient, Authentication authentication) {
        String providerType = oAuth2AuthorizedClient.getClientRegistration().getRegistrationId();
//        OAuth2AccessToken accessToken = oAuth2AuthorizedClient.getAccessToken();

        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        String id = oauth2User.getName();
        String name = oauth2User.getAttribute("name");
        String image = oauth2User.getAttribute("image");

        userRepository.findByOauthKey(id).orElseGet(() -> {
            User userBuilder = User.builder()
                    .oauthKey(id)
                    .name(name)
                    .profileImage(image)
                    .providerType(providerType)
                    .role(Role.USER)
                    .build();
            return userRepository.save(userBuilder);
        });
    }

    @Override
    public <T extends OAuth2AuthorizedClient> T loadAuthorizedClient(String clientRegistrationId, String principalName) {
        return null;
    }

    @Override
    public void removeAuthorizedClient(String clientRegistrationId, String principalName) {
    }
}