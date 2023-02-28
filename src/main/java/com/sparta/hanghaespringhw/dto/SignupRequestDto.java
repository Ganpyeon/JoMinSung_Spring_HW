package com.sparta.hanghaespringhw.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;



@Getter
@Setter
public class SignupRequestDto { // 회원 가입 하는 코드이다.

    @NotBlank // null, 빈칸 등등 전부 허용 하지 않기위해 not blank 사용
    @Pattern(regexp = "^[a-z0-9]{4,10}$", message = "유저 이름은 4~10자 영문 대 소문자, 숫자를 사용하세요.")
    private String username;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9~!@#$%^&*()_+=?,./<>{}\\[\\]\\-]{8,15}$", message = "비밀번호는 8~15자 영문 대 소문자, 숫자를 사용하세요.")
    private String password;


    private boolean admin = false; // 관리자 권한 받을건지에 대한 on off
    private String adminToken = "";
}
