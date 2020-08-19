package com.rubycon.rubyconteam2.core.config.oauth.handler;

import com.rubycon.rubyconteam2.core.jwt.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private JwtService jwtService;

    // 상수만 따로 저장하는 클래스 만들어서 분리해야할 듯
    static final String HEADER_STRING = "token";

    static final String TOKEN_PREFIX = "bearer ";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        String id = authentication.getName();
        String name = oauth2User.getAttribute("name");

        String token = jwtService.createToken(id, name);

        log.debug("Custom OAuth Handler  {} {}", id, name);
        log.debug("JWT token  {}", token);

        response.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
        response.setContentType("application/json");
        response.getWriter().print(token);
    }
}
