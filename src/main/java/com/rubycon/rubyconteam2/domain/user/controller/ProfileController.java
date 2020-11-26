package com.rubycon.rubyconteam2.domain.user.controller;

import com.rubycon.rubyconteam2.domain.review.domain.Review;
import com.rubycon.rubyconteam2.domain.review.service.ReviewService;
import com.rubycon.rubyconteam2.domain.user.domain.User;
import com.rubycon.rubyconteam2.domain.user.dto.response.ProfileResponse;
import com.rubycon.rubyconteam2.domain.user.dto.response.ProfileReviewResponse;
import com.rubycon.rubyconteam2.domain.user.service.UserService;
import com.rubycon.rubyconteam2.global.config.oauth.constants.OAuthConstants;
import com.rubycon.rubyconteam2.global.config.security.exception.AuthenticationException;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/profiles")
@RequiredArgsConstructor
@Validated // For @RequestParam Exception
public class ProfileController {

    private final ReviewService reviewService;
    private final UserService userService;

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "현재 로그인 된 사용자 정보 확인 API", notes = "로그인이 필요한 API\n 토큰을 담아 보내면 사용자 정보를 얻을 수 있습니다.")
    public ProfileResponse me(
            @AuthenticationPrincipal OAuth2User oAuth2User
    ){
        if (oAuth2User == null) throw new AuthenticationException();

        Long userId = oAuth2User.getAttribute(OAuthConstants.KEY);
        User user = userService.findById(userId);

        return new ProfileResponse(user);
    }

    // TODO : 권한 허용
    @GetMapping("/{userId}/reviews")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "특정 사용자의 모든 리뷰 조회 API")
    public ProfileReviewResponse findAllReview(
            @PathVariable final Long userId
    ){
        List<Review> reviews = reviewService.findAllReview(userId);

        return new ProfileReviewResponse(reviews);
    }
}
