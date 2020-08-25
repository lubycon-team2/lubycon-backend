package com.rubycon.rubyconteam2.core.config.security;

import com.rubycon.rubyconteam2.core.config.oauth.handler.OAuth2SuccessHandler;
import com.rubycon.rubyconteam2.core.config.oauth.usertypes.GoogleOAuth2User;
import com.rubycon.rubyconteam2.core.config.oauth.usertypes.KakaoOAuth2User;
import com.rubycon.rubyconteam2.core.config.security.filters.JwtAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public AuthenticationSuccessHandler successHandler(){
        return new OAuth2SuccessHandler();
    }

    @Bean
    public JwtAuthorizationFilter jwtFilter(){
        return new JwtAuthorizationFilter();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                    .cors()
                .and()
                    .formLogin().disable()
                    .csrf().disable()
                    .headers().frameOptions().disable()
                .and()
                    .authorizeRequests()
                    .antMatchers("/", "/login", "/oauth2/**", "/jwt/**").permitAll()
                    .anyRequest().authenticated()
                .and()
                .oauth2Login()
                    // customUserType을 추가하면, 내부적으로 'CustomUserTypesOAuth2UserService' 클래스 사용
                    .userInfoEndpoint()
                        .customUserType(KakaoOAuth2User.class, "kakao")
                        .customUserType(GoogleOAuth2User.class, "google")
                .and()
                // 이 부분에서 Success Handler를 설정합니다.
                    .successHandler(successHandler())
                    .failureUrl("/loginFailure")
                .and()
                    .exceptionHandling();

        http.addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class)
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
