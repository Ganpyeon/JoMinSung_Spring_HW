package com.sparta.hanghaespringhw.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequestDto { // 로그인할때 따로 저장해주는 코드
    private String username;
    private String password;
}
