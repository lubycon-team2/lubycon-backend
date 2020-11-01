package com.rubycon.rubyconteam2.domain.party_join.controller;

import com.rubycon.rubyconteam2.domain.party_join.domain.PartyJoin;
import com.rubycon.rubyconteam2.domain.party_join.dto.response.PartyJoinResponse;
import com.rubycon.rubyconteam2.domain.party_join.service.PartyJoinService;
import com.rubycon.rubyconteam2.domain.party_join.dto.request.MyPartyRequest;
import com.rubycon.rubyconteam2.domain.party_join.dto.response.PartyWithRoleResponse;
import com.rubycon.rubyconteam2.global.config.oauth.constants.OAuthConstants;
import com.rubycon.rubyconteam2.global.config.security.exception.AuthenticationException;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/party")
@RequiredArgsConstructor
@Slf4j
public class PartyJoinController {

    private final PartyJoinService partyJoinService;

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "내가 가입한 파티 조회 API")
    public List<PartyWithRoleResponse> findAllMyParty(
            @AuthenticationPrincipal OAuth2User oAuth2User,
            @RequestParam("partyState") @Valid MyPartyRequest profileDto
    ){
        if (oAuth2User == null) throw new AuthenticationException();

        Long userId = oAuth2User.getAttribute(OAuthConstants.KEY);
        List<PartyJoin> partyJoins = partyJoinService.findAllMyPartyByState(userId, profileDto.getPartyState());

        return partyJoins.stream()
                .map(PartyWithRoleResponse::new)
                .collect(Collectors.toList());
    }

    @PostMapping("/{partyId}/join")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "파티 참여하기 API")
    public PartyJoinResponse joinParty(
            @AuthenticationPrincipal OAuth2User oAuth2User,
            @PathVariable final Long partyId
    ) {
        if (oAuth2User == null) throw new AuthenticationException();

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
        if (oAuth2User == null) throw new AuthenticationException();

        Long userId = oAuth2User.getAttribute(OAuthConstants.KEY);
        partyJoinService.leave(userId, partyId);
    }

    @DeleteMapping("/{partyId}/users/{targetId}/kick-off")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "파티원 강퇴하기 API")
    public void kickOff(
            @AuthenticationPrincipal OAuth2User oAuth2User,
            @PathVariable final Long partyId,
            @PathVariable final Long targetId
    ) {
        if (oAuth2User == null) throw new AuthenticationException();

        Long userId = oAuth2User.getAttribute(OAuthConstants.KEY);
        partyJoinService.kickOff(userId, targetId, partyId);
    }
}
