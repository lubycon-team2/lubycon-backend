package com.rubycon.rubyconteam2.global.config.security.entrypoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rubycon.rubyconteam2.global.error.ErrorCode;
import com.rubycon.rubyconteam2.global.error.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * AuthenticationEntryPoint 인터페이스를 사용하며 인증이 필요한 resource에 엑세스 하려고 시도 할 때 실행됨
 */

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException e) throws IOException, ServletException {
        log.error(e.getMessage());
        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        res.setContentType(MediaType.APPLICATION_JSON.toString());
        res.setCharacterEncoding("UTF-8");
        res.getWriter().write(this.objectMapper.writeValueAsString(ErrorResponse.of(ErrorCode.UNAUTHORIZED)));
    }
}

