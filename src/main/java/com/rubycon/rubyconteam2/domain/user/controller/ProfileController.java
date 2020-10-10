package com.rubycon.rubyconteam2.domain.user.controller;

import com.rubycon.rubyconteam2.domain.party.domain.PartyJoin;
import com.rubycon.rubyconteam2.domain.party.service.PartyJoinService;
import com.rubycon.rubyconteam2.domain.user.dto.request.ProfilePartyRequest;
import com.rubycon.rubyconteam2.domain.user.dto.response.ProfilePartyResponse;
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
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/profiles")
@RequiredArgsConstructor
@Validated // For @RequestParam Exception
public class ProfileController {

    private final PartyJoinService partyJoinService;

    @GetMapping("/party")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "내가 가입한 파티 조회 API")
    public List<ProfilePartyResponse> findAllMyParty(
            @AuthenticationPrincipal OAuth2User oAuth2User,
            @RequestParam("partyState") @Valid ProfilePartyRequest profileDto
    ){
        if (oAuth2User == null) throw new AuthenticationException();

        Long userId = oAuth2User.getAttribute(OAuthConstants.KEY);
        List<PartyJoin> partyJoins = partyJoinService.findAllMyPartyByState(userId, profileDto.getPartyState());

        return partyJoins.stream()
                .map(ProfilePartyResponse::new)
                .collect(Collectors.toList());
    }
}
