package com.rubycon.rubyconteam2.global.common;

import com.rubycon.rubyconteam2.domain.user.domain.User;
import com.rubycon.rubyconteam2.global.config.oauth.usertype.GoogleOAuth2User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockCustomUserSecurityContextFactory
        implements WithSecurityContextFactory<WithMockCustomUser> {

    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        User user = User.builder()
            .userId(customUser.id())
            .oauthKey("TEST_OAUTH_KEY")
            .name("TEST")
            .profileUrl("https://google.com")
            .build();

        OAuth2User oAuth2User = new GoogleOAuth2User().build(user);
        Authentication auth =
                new UsernamePasswordAuthenticationToken(oAuth2User, null, oAuth2User.getAuthorities());
        context.setAuthentication(auth);
        return context;
    }
}