package com.sparta.hanghaespringhw.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CheckResponseDto {
    private int checkcode;
    private String checkmsg;


    public CheckResponseDto(int checkcode, String checkmsg){
        this.checkmsg = checkmsg;
        this.checkcode = checkcode;
    }
}
