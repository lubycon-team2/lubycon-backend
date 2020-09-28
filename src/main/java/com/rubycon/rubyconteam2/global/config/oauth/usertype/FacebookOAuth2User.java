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
public class FacebookOAuth2User implements OAuth2User {

    private String id;
    private String name;
    private FacebookFicture picture;

    public OAuth2User build(User user) {
        this.id = user.getOauthKey();
        this.name = user.getName();
        this.picture = new FacebookOAuth2User.FacebookFicture().build(user);

        return this;
    }

    @Override
    public Map<String, Object> getAttributes() {
        Map<String, Object> attrs = new HashMap<>();
        attrs.put(OAuthConstants.ID, this.id);
        attrs.put(OAuthConstants.NAME, this.name);
        attrs.put(OAuthConstants.IMAGE, this.picture.data.get("url"));

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
    public static class FacebookFicture {
        private Map<String, Object> data;

        public FacebookFicture build(User user) {
            data = new HashMap<>();
            data.put("url", user.getProfileUrl());
            return this;
        }
    }
}