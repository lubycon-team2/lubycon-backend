package com.rubycon.rubyconteam2.global.factory;

import com.rubycon.rubyconteam2.domain.user.domain.Role;
import com.rubycon.rubyconteam2.domain.user.domain.User;

import java.security.AuthProvider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestUserFactory {
    public static List<User> getTestUsers(){
        User user1 = User.builder()
                .userId(1L)
                .name("woowon")
                .profileUrl("https://google.com")
                .build();
        User user2 = User.builder()
                .userId(2L)
                .name("rubycon")
                .profileUrl("https://google.com")
                .build();
        User user3 = User.builder()
                .userId(3L)
                .name("partying")
                .profileUrl("https://kakao.com")
                .build();

        return new ArrayList<>(
                Arrays.asList(user1, user2, user3)
        );
    }

    public static User createUser(Long userId){
        return User.builder()
                .userId(userId)
                .name("Test Name")
                .profileUrl("https://url.link")
                .phoneNumber("010********")
                .oauthKey("Secret OAuth Key")
                .providerType("google")
                .role(Role.USER)
                .build();
    }

//    public static ProfileDto.UpdateReq createProfileDto(){
//        return ProfileDto.UpdateReq.builder()
//                .nickname("testNick")
//                .urlProfile("test Url")
//                .description("description")
//                .addrRss("rss")
//                .addrGithub("github")
//                .addrBlog("blog")
//                .build();
//    }
}
