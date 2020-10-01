package com.rubycon.rubyconteam2.global.config.security.filter;

import com.rubycon.rubyconteam2.domain.user.domain.User;
import com.rubycon.rubyconteam2.domain.user.exception.UserNotFoundException;
import com.rubycon.rubyconteam2.domain.user.repository.UserRepository;
import com.rubycon.rubyconteam2.global.config.oauth.usertype.FacebookOAuth2User;
import com.rubycon.rubyconteam2.global.config.oauth.usertype.GoogleOAuth2User;
import com.rubycon.rubyconteam2.global.config.oauth.usertype.KakaoOAuth2User;
import com.rubycon.rubyconteam2.global.config.security.constants.SecurityConstants;
import com.rubycon.rubyconteam2.global.core.jwt.exception.*;
import com.rubycon.rubyconteam2.global.core.jwt.service.JwtService;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    // TODO : UserService로 수정할 것
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        Authentication authentication = getAuthentication(request);
        if (authentication != null) {
            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private Claims getUserClaimsFromToken(String header) {
        try {
            if (header != null && header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
                String token = header.split(" ")[1];
                return jwtService.getPayloadsFromToken(token);
            }
        } catch (SignatureException e) {
            throw new JwtSignatureException();
        } catch (MalformedJwtException e) {
            throw new JwtMalformedException();
        } catch (ExpiredJwtException e) {
            throw new JwtExpiredException();
        } catch (UnsupportedJwtException e) {
            throw new JwtUnsupportedException();
        } catch (IllegalArgumentException e) {
            throw new JwtIllegalArgumentException();
        }
        return null;
    }

    // 헤더 토큰으로 사용자 정보가져와 스프링 인증 토큰 생성
    private Authentication getAuthentication(HttpServletRequest request) {
        final String header = request.getHeader(SecurityConstants.TOKEN_HEADER);

        Claims claims = getUserClaimsFromToken(header);
        if (claims == null) return null;
        String oauthKey = (String) claims.get("id");

        User user = userRepository.findByOauthKey(oauthKey)
                .orElseThrow(UserNotFoundException::new);

        OAuth2User oAuth2User = null;

        String providerType = user.getProviderType();
        // TODO : ENUM으로 바꿔보기? + static inner class 이슈 없는지
        // TODO : 필요한 부분인지 다시 생각해보기
        if (providerType.equals("google")) {
            oAuth2User = new GoogleOAuth2User().build(user);
        } else if (providerType.equals("kakao")) {
            oAuth2User = new KakaoOAuth2User().build(user);
        } else if (providerType.equals("facebook")) {
            oAuth2User = new FacebookOAuth2User().build(user);
        }

        if (oAuth2User != null) {
            log.debug(oAuth2User.getAttributes().toString());

            return new UsernamePasswordAuthenticationToken(oAuth2User, null, oAuth2User.getAuthorities());
        }
        return null;
    }

    //    @Override
    //    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    //        return new AntPathMatcher().match("/login", request.getServletPath());
    //    }
}
