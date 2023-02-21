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

    @GetMapping("/signup")
    public ModelAndView signupPage() {
        return new ModelAndView("signup");
    }

    @GetMapping("/login-page")
    public ModelAndView loginPage() {
        return new ModelAndView("login");
    }

    @PostMapping("/signup")
    public ResponseEntity<CheckResponseDto> signup(@Valid @RequestBody SignupRequestDto signupRequestDto, BindingResult bindingResult){ // 회원가입 코드이며 BindingReuslt는 유효성 검사를 했을때 에러가 나왔을때 그걸 받아주는 역할이다,
        if (bindingResult.hasErrors()) {//hassErrors가 boolen변수로 되어있는데 이건 오류가 있으면 true 없으면 false로 반환을 해준다.
            throw new IllegalArgumentException("로그인 조건이 맞지 않습니다.");// 여기서 오류가 떳을때 예외 처리로 이런식으로 반환을 해주라는 코드이다.
        }

        return userService.signup(signupRequestDto);//오류가 잡히지 않았을때는 회원 가입을 신청해준다.
    }

    @ResponseBody
    @PostMapping("/login")
    public ResponseEntity<CheckResponseDto> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {

        return userService.login(loginRequestDto, response); // 로그인 하기위한 데이터와 jwt token을 받기위한 RESPONSE를 SERVERLET으로 보내서 로그인도 하고 유저의 고유 JWT TOKEN도 받아와줌 이걸로 게시물을 만들때 자기만의 권한이 생긴 게시물을 만들수 있음
    }

}
