package com.rubycon.rubyconteam2.domain.party.controller;

import com.rubycon.rubyconteam2.domain.party.domain.Party;
import com.rubycon.rubyconteam2.domain.party.dto.request.PartyCreateRequest;
import com.rubycon.rubyconteam2.domain.party.dto.request.PartyFindRequest;
import com.rubycon.rubyconteam2.domain.party.dto.request.PartyUpdateRequest;
import com.rubycon.rubyconteam2.domain.party.dto.response.PartyResponse;
import com.rubycon.rubyconteam2.domain.party.service.PartyService;
import com.rubycon.rubyconteam2.global.config.oauth.constants.OAuthConstants;
import com.rubycon.rubyconteam2.global.error.exception.NoContentException;
import io.swagger.annotations.ApiImplicitParam;
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
@RequestMapping("/party")
@RequiredArgsConstructor
@Validated // For @RequestParam Exception
public class PartyController {

    private final PartyService partyService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "서비스 별 파티 목록 조회 API")
    @ApiImplicitParam(name = "Authorization", value = "Not Required")
    public List<PartyResponse> findAllParty(
            @RequestParam("serviceType") @Valid PartyFindRequest partyDto
    ) {
        List<Party> partyList = partyService.findAll(partyDto.getServiceType());
        if (partyList.isEmpty()) throw new NoContentException();

        return partyList.stream()
                .map(PartyResponse::new)
                .collect(Collectors.toList());
    }

    // TODO : 파티 생성할 때 해당 유저 파티장으로 권한 상승하기
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "파티 생성 API")
    public PartyResponse saveParty(
            @AuthenticationPrincipal OAuth2User oAuth2User,
            @RequestBody @Valid PartyCreateRequest partyDto
    ) {
        Long userId = oAuth2User.getAttribute(OAuthConstants.KEY);
        Party party = partyService.save(userId, partyDto);
        return new PartyResponse(party);
    }

    @GetMapping("/{partyId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "특정 파티 1개 조회 API")
    public PartyResponse findParty(
            @PathVariable final Long partyId
    ){
        Party party = partyService.findById(partyId);
        return new PartyResponse(party);
    }

    @PutMapping("/{partyId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "파티 업데이트 API")
    public PartyResponse updateParty(
            @PathVariable final Long partyId,
            @RequestBody @Valid PartyUpdateRequest partyDto
    ){
        Party party = partyService.update(partyId, partyDto);
        return new PartyResponse(party);
    }

    @DeleteMapping("/{partyId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "파티 삭제 API")
    public void deleteParty(
            @AuthenticationPrincipal OAuth2User oAuth2User,
            @PathVariable final Long partyId
    ){
        Long userId = oAuth2User.getAttribute(OAuthConstants.KEY);
        partyService.delete(userId, partyId);
    }
}
