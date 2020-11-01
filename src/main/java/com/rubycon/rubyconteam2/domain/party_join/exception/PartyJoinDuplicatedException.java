package com.rubycon.rubyconteam2.domain.party_join.exception;

import com.rubycon.rubyconteam2.global.error.ErrorCode;
import com.rubycon.rubyconteam2.global.error.exception.BusinessException;

public class PartyJoinDuplicatedException extends BusinessException {
    public PartyJoinDuplicatedException() {
        super(ErrorCode.PARTY_JOIN_DUPLICATED);
    }
}