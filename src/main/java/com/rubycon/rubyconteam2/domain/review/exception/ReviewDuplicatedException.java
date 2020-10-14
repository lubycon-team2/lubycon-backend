package com.rubycon.rubyconteam2.domain.review.exception;

import com.rubycon.rubyconteam2.global.error.ErrorCode;
import com.rubycon.rubyconteam2.global.error.exception.BusinessException;

public class ReviewDuplicatedException extends BusinessException {
    public ReviewDuplicatedException() {
        super(ErrorCode.REVIEW_DUPLICATED);
    }
}