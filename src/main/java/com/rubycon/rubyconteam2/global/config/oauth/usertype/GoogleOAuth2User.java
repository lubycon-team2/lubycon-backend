package com.rubycon.rubyconteam2.global.config.oauth.usertype;

import com.rubycon.rubyconteam2.domain.user.domain.User;
import com.rubycon.rubyconteam2.global.config.oauth.constants.OAuthConstants;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

// CustomUserType 추가 - OAuth2User 인터페이스를 구현해서 생성

@Getter
public class GoogleOAuth2User implements OAuth2User {

    private String sub;
    private String name;
    private String picture;

    // Using for build method
    // TODO : 어떻게 처리할지 ?, 인증에 oauthkey를 쓸지, userId를 쓸지 결정하기
    private Long key;

    public OAuth2User build(User user) {
        this.sub = user.getOauthKey();
        this.key = user.getUserId();
        this.name = user.getName();
        this.picture = user.getProfileUrl();
        return this;
    }

    @Override
    public Map<String, Object> getAttributes() {
        Map<String, Object> attrs = new HashMap<>();
        attrs.put(OAuthConstants.ID, this.sub);
        attrs.put(OAuthConstants.KEY, this.key);
        attrs.put(OAuthConstants.NAME, this.name);
        attrs.put(OAuthConstants.IMAGE, this.picture);

        return attrs;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new OAuth2UserAuthority(getAttributes()));
    }

    @Override
    public String getName() {
        return this.sub;
    }
}