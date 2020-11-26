package com.rubycon.rubyconteam2.domain.review.controller;

import com.rubycon.rubyconteam2.domain.party_join.domain.PartyJoin;
import com.rubycon.rubyconteam2.domain.party_join.dto.response.PartyDetailsResponse;
import com.rubycon.rubyconteam2.domain.party_join.service.PartyJoinService;
import com.rubycon.rubyconteam2.domain.review.dto.request.ReviewRequest;
import com.rubycon.rubyconteam2.domain.review.service.ReviewService;
import com.rubycon.rubyconteam2.domain.user.dto.response.ProfileWithRoleResponse;
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
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/party")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final PartyJoinService partyJoinService;

    @GetMapping("/{partyId}/users/reviewable")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "현재 내가 리뷰 가능한 사용자 리스트 조회 API")
    public List<ProfileWithRoleResponse> findReviewableUsers(
            @AuthenticationPrincipal OAuth2User oAuth2User,
            @PathVariable final Long partyId
    ){
        if (oAuth2User == null) throw new AuthenticationException();

        Long userId = oAuth2User.getAttribute(OAuthConstants.KEY);
        return partyJoinService.findAllReviewableUsers(userId, partyId);
    }

    @PostMapping("/{partyId}/users/{targetId}/review")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "같은 파티에 참여한 적이 있는 특정 사용자에게 리뷰 하기 API")
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
