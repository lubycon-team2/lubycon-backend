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

@Getter
public class KakaoOAuth2User implements OAuth2User {

    private String id;
    private KakaoProperties properties;

    // Using for build method
    private Long key;

    public OAuth2User build(User user) {
        this.id = user.getOauthKey();
        this.key = user.getUserId();
        this.properties = new KakaoOAuth2User.KakaoProperties().build(user);
        return this;
    }

    @Override
    public Map<String, Object> getAttributes() {
        Map<String, Object> attrs = new HashMap<>();
        attrs.put(OAuthConstants.ID, this.id);
        attrs.put(OAuthConstants.KEY, this.key);
        attrs.put(OAuthConstants.NAME, this.properties.getNickname());
        attrs.put(OAuthConstants.IMAGE, this.properties.getProfile_image());

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
    public static class KakaoProperties {
        private String nickname;
        private String profile_image;

        public KakaoProperties build(User user){
            this.nickname = user.getName();
            this.profile_image = user.getProfileUrl();
            return this;
        }
    }
}