package com.rubycon.rubyconteam2.infra.sms;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class NCPMessage implements Serializable {
    private String to;
}