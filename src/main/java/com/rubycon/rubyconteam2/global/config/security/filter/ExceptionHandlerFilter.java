package com.rubycon.rubyconteam2.global.config.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rubycon.rubyconteam2.global.error.ErrorCode;
import com.rubycon.rubyconteam2.global.error.ErrorResponse;
import com.rubycon.rubyconteam2.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Security Exception Handler
 * Security의 에러 핸들러 필터
 */

@Component
@RequiredArgsConstructor
@Slf4j
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (BusinessException e) {
            log.error(e.getMessage());
            sendErrorResponse(response, e.getErrorCode());
        }
    }

    /**
     * JWT Security Error Response Message
     *
     * @param res
     * @param errorCode
     * @throws IOException
     */
    private void sendErrorResponse(HttpServletResponse res, ErrorCode errorCode) throws IOException {
        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        res.setContentType(MediaType.APPLICATION_JSON.toString());
        res.setCharacterEncoding("UTF-8");
        res.getWriter().write(this.objectMapper.writeValueAsString(ErrorResponse.of(errorCode)));
    }

}
