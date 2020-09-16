package com.rubycon.rubyconteam2.global.config.oauth.provider;

import com.rubycon.rubyconteam2.global.config.security.constants.SecurityConstants;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

public enum CustomOAuth2Provider {

    KAKAO {
        @Override
        public ClientRegistration.Builder getBuilder() {
            return getBuilder("kakao", ClientAuthenticationMethod.POST)
                    .scope("profile") // 요청할 권한
                    .authorizationUri(SecurityConstants.KAKAO_AUTHORIZATION_URL) // authorization code 얻는 API
                    .tokenUri(SecurityConstants.KAKAO_TOKEN_URL) // access Token 얻는 API
                    .userInfoUri(SecurityConstants.KAKAO_USERINFO_URL) // 유저 정보 조회 API
                    .userNameAttributeName("id") // userInfo API Response에서 얻어올 ID 프로퍼티
                    .clientName("Kakao"); // spring 내에서 인식할 OAuth2 Provider Name
        }
    };

    protected final ClientRegistration.Builder getBuilder(String registrationId,
                                                          ClientAuthenticationMethod method) {

        ClientRegistration.Builder builder = ClientRegistration.withRegistrationId(registrationId);
        builder.clientAuthenticationMethod(method);
        builder.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE);
        builder.redirectUriTemplate(SecurityConstants.DEFAULT_LOGIN_REDIRECT_URL);
        return builder;
    }

    public abstract ClientRegistration.Builder getBuilder();
}
