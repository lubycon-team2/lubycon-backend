package com.rubycon.rubyconteam2.core.config.security.filters;

import com.rubycon.rubyconteam2.core.config.oauth.usertypes.FacebookOAuth2User;
import com.rubycon.rubyconteam2.core.config.oauth.usertypes.GoogleOAuth2User;
import com.rubycon.rubyconteam2.core.config.oauth.usertypes.KakaoOAuth2User;
import com.rubycon.rubyconteam2.core.config.security.SecurityConstants;
import com.rubycon.rubyconteam2.core.jwt.JwtService;
import com.rubycon.rubyconteam2.domain.user.dao.UserRepository;
import com.rubycon.rubyconteam2.domain.user.domain.User;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        Authentication authentication = getAuthentication(request);
        if (authentication != null){
            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private Claims getUserClaimsFromToken(String header){
        if (header != null && header.startsWith(SecurityConstants.TOKEN_PREFIX)){
            String token = header.split(" ")[1];
            return jwtService.getPayloadsFromToken(token);
        }
        return null;
    }

    // 헤더 토큰으로 사용자 정보가져와 스프링 인증 토큰 생성
    private Authentication getAuthentication(HttpServletRequest request){
        final String header = request.getHeader(SecurityConstants.TOKEN_HEADER);

        Claims claims = getUserClaimsFromToken(header);
        if(claims == null) return null;
        String oauthKey = (String) claims.get("id");

        User user = userRepository.findByOauthKey(oauthKey).orElseThrow(() -> new RuntimeException("Not Found User!"));

        OAuth2User oAuth2User = null;

        String providerType = user.getProviderType();
        // TODO : ENUM으로 바꿔보기? + static inner class 이슈 없는지
        if (providerType.equals("google")){
            oAuth2User = new GoogleOAuth2User().build(user);
        } else if(providerType.equals("kakao")){
            oAuth2User = new KakaoOAuth2User().build(user);
        } else if(providerType.equals("facebook")){
            oAuth2User = new FacebookOAuth2User().build(user);
        }

        if (oAuth2User != null){
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
