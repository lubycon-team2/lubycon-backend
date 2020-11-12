package com.rubycon.rubyconteam2.global.factory;

import com.rubycon.rubyconteam2.domain.party.domain.*;
import com.rubycon.rubyconteam2.domain.party.dto.request.PartyCreateRequest;
import com.rubycon.rubyconteam2.domain.party.dto.request.PartyFindRequest;
import com.rubycon.rubyconteam2.domain.party.dto.request.PartyUpdateRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestPartyFactory {

    public static Party createParty(Long partyId, ServiceType type) {
        final String title = partyId + ". " + type.getTitle() + " Party Recruiting !";
        return Party.builder()
                .partyId(partyId)
                .title(title)
                .memberCount(0)
                .memberPrice(3500)
                .leaderPrice(3200)
                .kakaoOpenChatUrl("https://open.kakao.com/o/random")
                .partyPeriod(PartyPeriod.MONTH_1)
                .paymentCycle(PaymentCycle.MONTH_1)
                .partyState(PartyState.RECRUITING)
                .serviceType(type)
                .build();
    }

    public static PartyCreateRequest createPartyDto() {
        return PartyCreateRequest.builder()
                .title("넷플릭스 파티 모집")
                .memberPrice(3500)
                .leaderPrice(3200)
                .kakaoOpenChatUrl("https://open.kakao.com/o/random")
                .partyPeriod(PartyPeriod.MONTH_1.name())
                .paymentCycle(PaymentCycle.MONTH_3.name())
                .serviceType(ServiceType.NETFLIX.name())
                .build();
    }

    public static PartyUpdateRequest updatePartyDto() {
        return PartyUpdateRequest.builder()
                .title("Update NETFLIX Party Recruiting !")
                .memberPrice(3500)
                .leaderPrice(3200)
                .kakaoOpenChatUrl("https://open.kakao.com/o/random")
                .partyPeriod(PartyPeriod.MONTH_6.name())
                .paymentCycle(PaymentCycle.MONTH_6.name())
                .build();
    }

    public static PartyFindRequest findPartyDto() {
        return PartyFindRequest.builder()
                .serviceType(ServiceType.NETFLIX.name())
                .build();
    }

    public static List<Party> findAllNetflixParties() {
        return new ArrayList<>(
                Arrays.asList(
                        createParty(1L, ServiceType.NETFLIX),
                        createParty(2L, ServiceType.NETFLIX),
                        createParty(3L, ServiceType.NETFLIX),
                        createParty(4L, ServiceType.NETFLIX)
                ));
    }

//    public static Post findPost() {
//        User user = User.builder()
//                .userId(1L)
//                .nickname("Test User")
//                .email("Test Email")
//                .urlProfile("https://url.link")
//                .build();
//
//        return createPost(1L, user, null);
//    }
}
