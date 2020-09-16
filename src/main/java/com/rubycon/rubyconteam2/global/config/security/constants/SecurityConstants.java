package com.rubycon.rubyconteam2.global.config.security.constants;

public final class SecurityConstants {

    // Redirect URL
    public static final String DEFAULT_LOGIN_REDIRECT_URL = "{baseUrl}/login/oauth2/code/{registrationId}";

    // Redirect Client URL
    public static final String CLIENT_REDIRECT_URL = "http://rubycon-team-2.s3-website.ap-northeast-2.amazonaws.com/oauth2/redirect";

    // KAKAO URL
    public static final String KAKAO_AUTHORIZATION_URL = "https://kauth.kakao.com/oauth/authorize";
    public static final String KAKAO_TOKEN_URL = "https://kauth.kakao.com/oauth/token";
    public static final String KAKAO_USERINFO_URL = "https://kapi.kakao.com/v2/user/me";

    // JWT token defaults
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_TYPE = "JWT";
    public static final String TOKEN_ALGORITHM = "HS256";
    public static final long EXPIRE_TIME = 1000 * 60 * 60 * 8; // 8시간

    private SecurityConstants() {
        throw new IllegalStateException("Cannot create instance of static util class");
    }
}
