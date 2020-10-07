package com.rubycon.rubyconteam2.domain.party.controller;

import com.rubycon.rubyconteam2.domain.party.domain.PartyJoin;
import com.rubycon.rubyconteam2.domain.party.dto.response.PartyJoinResponse;
import com.rubycon.rubyconteam2.domain.party.service.PartyJoinService;
import com.rubycon.rubyconteam2.global.config.oauth.constants.OAuthConstants;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/party")
@RequiredArgsConstructor
public class PartyJoinController {

    private final PartyJoinService partyJoinService;

    @PostMapping("/{partyId}/join")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "파티 참여하기 API")
    public PartyJoinResponse joinParty(
            @AuthenticationPrincipal OAuth2User oAuth2User,
            @PathVariable final Long partyId
    ) {
        Long userId = oAuth2User.getAttribute(OAuthConstants.KEY);
        PartyJoin partyJoin = partyJoinService.join(userId, partyId);
        return new PartyJoinResponse(partyJoin);
    }

    @DeleteMapping("/{partyId}/leave")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "파티 탈퇴하기 API")
    public void leaveParty(
            @AuthenticationPrincipal OAuth2User oAuth2User,
            @PathVariable final Long partyId
    ) {
        Long userId = oAuth2User.getAttribute(OAuthConstants.KEY);
        partyJoinService.leave(userId, partyId);
    }
}
