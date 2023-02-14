package com.sparta.hanghaespringhw.controller;

import com.sparta.hanghaespringhw.dto.CheckResponseDto;
import com.sparta.hanghaespringhw.dto.LoginRequestDto;
import com.sparta.hanghaespringhw.dto.SignupRequestDto;
import com.sparta.hanghaespringhw.service.UserService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<CheckResponseDto> signup(@Valid @RequestBody SignupRequestDto signupRequestDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException("로그인 조건이 맞지 않습니다.");
        }

        return  userService.signup(signupRequestDto);
    }

    @ResponseBody
    @PostMapping("/login")
    public ResponseEntity<CheckResponseDto> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {

        return userService.login(loginRequestDto, response);
    }

}
