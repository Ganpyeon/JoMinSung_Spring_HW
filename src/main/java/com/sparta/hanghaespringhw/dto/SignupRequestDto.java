package com.sparta.hanghaespringhw.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Getter
@Setter
public class SignupRequestDto {

    @NotBlank
    @Size(min = 4 , max = 10)
    @Pattern(regexp = "^[a-z0-9]{4,10}$", message = "유저 이름은 4~10자 영문 대 소문자, 숫자를 사용하세요.")
    private String username;

    @NotBlank
    @Size(min = 8 , max = 15)
    @Pattern(regexp = "^[a-zA-Z0-9~!@#$%^&*()_+=?,./<>{}\\[\\]\\-]{8,15}$", message = "비밀번호는 8~15자 영문 대 소문자, 숫자를 사용하세요.")
    private String password;

}
