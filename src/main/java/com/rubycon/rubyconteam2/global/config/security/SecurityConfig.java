package com.rubycon.rubyconteam2.global.config.security;

import com.rubycon.rubyconteam2.global.config.oauth.handler.OAuth2SuccessHandler;
import com.rubycon.rubyconteam2.global.config.oauth.usertype.FacebookOAuth2User;
import com.rubycon.rubyconteam2.global.config.oauth.usertype.GoogleOAuth2User;
import com.rubycon.rubyconteam2.global.config.oauth.usertype.KakaoOAuth2User;
import com.rubycon.rubyconteam2.global.config.security.entrypoint.JwtAuthenticationEntryPoint;
import com.rubycon.rubyconteam2.global.config.security.filter.ExceptionHandlerFilter;
import com.rubycon.rubyconteam2.global.config.security.filter.JwtAuthorizationFilter;
import com.rubycon.rubyconteam2.global.core.jwt.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    private final JwtAuthorizationFilter jwtAuthorizationFilter;

    private final ExceptionHandlerFilter exceptionHandlerFilter;

    private final JwtAuthenticationEntryPoint unauthorizedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                    .cors()
                .and()
                    .formLogin().disable()
                    .csrf().disable()
                    .authorizeRequests()
                    .antMatchers(
                        "/",
                        "/login",
                        "/oauth2/**",
                        "/jwt/**",
                        "/authenticate/**",
                        "/party/**",
                        // Swagger settings
                        "/v2/api-docs",
                        "/api-docs/**",
                        "/swagger-resources/**",
                        "/configuration/**",
                        "/webjars/**",
                        "/**/*.html"
                    ).permitAll()
                    .anyRequest().authenticated()
                .and()
                    .oauth2Login()
                        // customUserType을 추가하면, 내부적으로 'CustomUserTypesOAuth2UserService' 클래스 사용
                        .userInfoEndpoint()
                            .customUserType(KakaoOAuth2User.class, "kakao")
                            .customUserType(GoogleOAuth2User.class, "google")
                            .customUserType(FacebookOAuth2User.class, "facebook")
                    .and()
                        // 이 부분에서 Success Handler를 설정합니다.
                        .successHandler(oAuth2SuccessHandler)
                    .and()
                        .exceptionHandling().authenticationEntryPoint(unauthorizedHandler);

        http
                    .sessionManagement()
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                    .addFilterBefore(exceptionHandlerFilter, JwtAuthorizationFilter.class);

    }
}
