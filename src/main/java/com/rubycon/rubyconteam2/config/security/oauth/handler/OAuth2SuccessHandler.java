package com.rubycon.rubyconteam2.config.security.oauth.handler;

//public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
//        String id = authentication.getName();
//        String name = oauth2User.getAttribute("name");
//
//        String token = JWT.create()
//                .withClaim("id", id)
//                .withClaim("name", name)
//                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
//                .sign(Algorithm.HMAC512(SecurityConstants.SECRET.getBytes()));
//
//        response.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
//    }
//}
