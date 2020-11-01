package com.rubycon.rubyconteam2.domain.party_join.exception;

import com.rubycon.rubyconteam2.global.error.ErrorCode;
import com.rubycon.rubyconteam2.global.error.exception.BusinessException;

public class PartyJoinNotFoundException extends BusinessException {
    public PartyJoinNotFoundException() {
        super(ErrorCode.PARTY_JOIN_NOT_FOUND);
    }
}