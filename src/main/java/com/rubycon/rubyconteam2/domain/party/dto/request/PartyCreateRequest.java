package com.rubycon.rubyconteam2.domain.party.dto.request;

import com.rubycon.rubyconteam2.domain.party.domain.Party;
import com.rubycon.rubyconteam2.domain.party.domain.PartyState;
import com.rubycon.rubyconteam2.domain.party.domain.ServiceType;
import com.rubycon.rubyconteam2.global.common.model.ValueOfEnum;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class PartyCreateRequest {

    @NotEmpty(message = "파티 제목을 입력해주세요.")
    private String title;

    @Size(max = 4000, message
            = "파티 설명글은 4000자보다 작아야합니다.")
    private String description;

    @NotNull(message= "파티의 최대 인원을 입력해주세요.")
    @Min(0) @Max(8)
    private Integer maxPartyNumber;

    @NotNull(message= "결제 주기를 입력해주세요.")
    @Min(0)
    private Integer paymentCycle;

    @NotNull(message= "1인 당 결제 금액을 입력해주세요.")
    @Min(0)
    private Integer pricePerPerson;

    @NotEmpty(message = "서비스 타입을 입력해주세요 \nNETFLIX | WATCHA | WAAVE | APPLE_MUSIC")
    @ValueOfEnum(enumClass = ServiceType.class)
    private String serviceType;

    public Party toEntity() {
        return Party.builder()
                .title(title)
                .description(description)
                .maxPartyNumber(maxPartyNumber)
                .paymentCycle(paymentCycle)
                .pricePerPerson(pricePerPerson)
                .serviceType(ServiceType.valueOf(serviceType))
                .partyState(PartyState.RECRUITING)
                .build();
    }
}
