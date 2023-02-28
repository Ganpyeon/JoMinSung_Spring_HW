package com.sparta.hanghaespringhw.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CheckResponseDto { // 예외처리 할때 상태 코드와 상태 코드에 대한 메세지를 전달하기 위해 만든 Dto
    private int checkcode;
    private String checkmsg;



    public CheckResponseDto(int checkcode, String checkmsg){
        this.checkmsg = checkmsg;
        this.checkcode = checkcode;
    }
}
