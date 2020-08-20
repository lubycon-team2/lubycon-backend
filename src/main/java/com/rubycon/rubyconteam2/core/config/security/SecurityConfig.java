package com.rubycon.rubyconteam2.core.config.security;

import com.rubycon.rubyconteam2.core.config.oauth.handler.OAuth2SuccessHandler;
import com.rubycon.rubyconteam2.core.config.oauth.usertype.GoogleOAuth2User;
import com.rubycon.rubyconteam2.core.config.oauth.usertype.KakaoOAuth2User;
import com.rubycon.rubyconteam2.core.jwt.JwtService;
import com.rubycon.rubyconteam2.core.jwt.JwtServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return new OAuth2SuccessHandler();
    }

    @Bean
    public JwtService jwtService(){
        return new JwtServiceImpl();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                    .cors()
                .and()
//                .sessionManagement()
//                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                    .and()
                    .csrf().disable()
                    .headers().frameOptions().disable()
                .and()
                    .authorizeRequests()
                    .antMatchers("/", "/oauth2/**", "/jwt/**").permitAll()
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
    }
}
