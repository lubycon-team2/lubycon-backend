package com.rubycon.rubyconteam2.infra.sms;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class NCPVerifyRequest implements Serializable {
    private String to;
    private String code;
}
