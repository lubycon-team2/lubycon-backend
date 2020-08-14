package com.rubycon.rubyconteam2.config.security.oauth.usertype;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Getter
public class KakaoOAuth2User implements OAuth2User {
    private String id;
    private KakaoProperties properties;

    @Override
    public Map<String, Object> getAttributes() {
        Map<String, Object> attrs = new HashMap<>();
        attrs.put("id", this.id);
        attrs.put("name", this.properties.getNickname());
        attrs.put("image", this.properties.getThumbnail_image());

        return attrs;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new OAuth2UserAuthority(getAttributes()));
    }

    @Override
    public String getName() {
        return this.id;
    }

    @Getter
    private static class KakaoProperties {
        private String nickname;
        private String thumbnail_image;
    }
}