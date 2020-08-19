package com.rubycon.rubyconteam2.core.config.oauth.usertype;

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

    @Override
    public Map<String, Object> getAttributes() {
        Map<String, Object> attrs = new HashMap<>();
        attrs.put("id", this.sub);
        attrs.put("name", this.name);
        attrs.put("image", this.picture);

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