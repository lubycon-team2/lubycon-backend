package com.rubycon.rubyconteam2.hello.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HelloResponseDto {
    private String name;
    private int amount;

    public HelloResponseDto(String name, int amount){
        this.name = name;
        this.amount = amount;
    }
}
