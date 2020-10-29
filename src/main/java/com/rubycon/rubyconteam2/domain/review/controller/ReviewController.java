package com.rubycon.rubyconteam2.domain.review.controller;

import com.rubycon.rubyconteam2.domain.review.dto.request.ReviewRequest;
import com.rubycon.rubyconteam2.domain.review.service.ReviewService;
import com.rubycon.rubyconteam2.global.config.oauth.constants.OAuthConstants;
import com.rubycon.rubyconteam2.global.config.security.exception.AuthenticationException;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/party")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/{partyId}/users/{targetId}/review")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "특정 사용자에게 리뷰 하기 API")
    public void review(
            @AuthenticationPrincipal OAuth2User oAuth2User,
            @PathVariable final Long partyId,
            @PathVariable final Long targetId,
            @RequestBody @Valid ReviewRequest reviewDto
    ) {
        if (oAuth2User == null) throw new AuthenticationException();

        Long reviewerId = oAuth2User.getAttribute(OAuthConstants.KEY);
        reviewService.review(reviewerId, targetId, partyId, reviewDto);
    }
}
