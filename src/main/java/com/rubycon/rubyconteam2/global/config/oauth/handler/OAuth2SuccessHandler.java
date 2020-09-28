package com.rubycon.rubyconteam2.global.config.oauth.handler;

import com.rubycon.rubyconteam2.global.config.oauth.constants.OAuthConstants;
import com.rubycon.rubyconteam2.global.config.security.constants.SecurityConstants;
import com.rubycon.rubyconteam2.global.core.jwt.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        String id = authentication.getName();
        String name = oauth2User.getAttribute(OAuthConstants.NAME);
        String image = oauth2User.getAttribute(OAuthConstants.IMAGE);

        String token = jwtService.createToken(id, name);

        log.debug("Login Success : {} {} {}", id, name, image);
        log.debug("JWT token : {}", token);

        String url = UriComponentsBuilder.fromUriString(SecurityConstants.CLIENT_REDIRECT_URL)
                .queryParam("access_token", Base64.getEncoder().encodeToString(token.getBytes()))
                .build().toUriString();
        log.debug("Redirect URL {}", url);

        response.sendRedirect(url);
    }
}
