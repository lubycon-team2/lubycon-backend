package com.rubycon.rubyconteam2.infra.sms;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NCPMessage {
    private String to;
    private String contents;
}
