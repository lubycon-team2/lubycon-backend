package com.rubycon.rubyconteam2.domain.party.controller;

import com.rubycon.rubyconteam2.domain.party.dto.request.PartyCreateRequest;
import com.rubycon.rubyconteam2.domain.party.dto.request.PartyFindRequest;
import com.rubycon.rubyconteam2.domain.party.dto.request.PartyUpdateRequest;
import com.rubycon.rubyconteam2.domain.party.dto.response.PartyResponse;
import com.rubycon.rubyconteam2.domain.party.service.PartyService;
import com.rubycon.rubyconteam2.domain.party_join.dto.response.PartyDetailsResponse;
import com.rubycon.rubyconteam2.domain.party_join.service.PartyJoinService;
import com.rubycon.rubyconteam2.global.common.page.PageRequest;
import com.rubycon.rubyconteam2.global.config.oauth.constants.OAuthConstants;
import com.rubycon.rubyconteam2.global.config.security.exception.AuthenticationException;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/party")
@RequiredArgsConstructor
@Validated // For @RequestParam Exception
public class PartyController {

    private final PartyService partyService;
    private final PartyJoinService partyJoinService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "서비스 별 파티 목록 조회 API")
    @ApiImplicitParam(name = "Authorization", value = "Not Required")
    public Page<PartyResponse> findAllParty(
            @RequestParam("serviceType") @Valid PartyFindRequest partyDto,
            final PageRequest pageRequest
    ) {
        return partyService.findAll(partyDto.getServiceType(), pageRequest);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "파티 생성 API")
    public PartyResponse saveParty(
            @AuthenticationPrincipal OAuth2User oAuth2User,
            @RequestBody @Valid PartyCreateRequest partyDto
    ) {
        if (oAuth2User == null) throw new AuthenticationException();

        Long userId = oAuth2User.getAttribute(OAuthConstants.KEY);
        return partyService.save(userId, partyDto);
    }

    @GetMapping("/{partyId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "파티 상세 조회 API - 파티 정보 + 참여자 정보")
    public PartyDetailsResponse findPartyDetails(
            @PathVariable final Long partyId
    ){
        return partyJoinService.findAllByPartyId(partyId);
    }

    @PutMapping("/{partyId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "파티 업데이트 API")
    public PartyResponse updateParty(
            @PathVariable final Long partyId,
            @RequestBody @Valid PartyUpdateRequest partyDto
    ){
        return partyService.update(partyId, partyDto);
    }

    @DeleteMapping("/{partyId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "파티 종료하기 API")
    public void deleteParty(
            @AuthenticationPrincipal OAuth2User oAuth2User,
            @PathVariable final Long partyId
    ){
        if (oAuth2User == null) throw new AuthenticationException();

        Long userId = oAuth2User.getAttribute(OAuthConstants.KEY);
        partyService.delete(userId, partyId);
    }
}
