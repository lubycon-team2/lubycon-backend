package com.rubycon.rubyconteam2.config.security;

import com.rubycon.rubyconteam2.config.security.kakao.KakaoOAuth2User;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                    .and()
                .authorizeRequests()
                .antMatchers("/oauth2/**", "/me").permitAll()
                .anyRequest().authenticated()
                .and()
                    .oauth2Login()
                        // customUserType을 추가하면, 내부적으로 'CustomUserTypesOAuth2UserService' 클래스 사용
                        .userInfoEndpoint()
                        .customUserType(KakaoOAuth2User.class, "kakao");

//        http
//                .cors()
//                    .and()
//                .sessionManagement()
//                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                    .and()
//                .csrf().disable()
//                .formLogin().disable()
//                .headers().frameOptions().disable()
//                .and()
//                    .authorizeRequests()
//                    .antMatchers("/", "/h2-console/**").permitAll()
//                    .antMatchers("/auth/**", "/oauth2/**").permitAll()
//                    .antMatchers("/api/v1/**").hasRole(Role.USER.name())
//                    .anyRequest().authenticated()
//                .and()
//                    .logout()
//                        .logoutSuccessUrl("/")
//                .and()
//                    .oauth2Login()
//                        .authorizationEndpoint()
//                            .baseUri("/oauth2/authorize")
//                            .and()
//                        .userInfoEndpoint()
//                            .userService(customOAuth2UserService);
    }
}
