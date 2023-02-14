package com.sparta.hanghaespringhw.service;

import com.sparta.hanghaespringhw.dto.CheckResponseDto;
import com.sparta.hanghaespringhw.dto.LoginRequestDto;
import com.sparta.hanghaespringhw.dto.SignupRequestDto;
import com.sparta.hanghaespringhw.entity.User;
import com.sparta.hanghaespringhw.jwt.JwtUtil;
import com.sparta.hanghaespringhw.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    private ResponseEntity<CheckResponseDto> badRequest(String msg){
        return ResponseEntity.badRequest().body(CheckResponseDto.builder()
                .checkmsg(msg)
                .checkcode(HttpStatus.BAD_REQUEST.value())
                .build());
    }

    @Transactional
    public ResponseEntity<CheckResponseDto> signup(SignupRequestDto signupRequestDto) {

        // 회원 중복 확인
        Optional<User> found = userRepository.findByUsername(signupRequestDto.getUsername());
        if (found.isPresent()) {
            return badRequest("중복된 사용자가 존재합니다.");
        }


        User user = new User(signupRequestDto);
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body(new CheckResponseDto(HttpStatus.OK.value(),"회원가입이 완료 되었습니다."));
    }

    @Transactional(readOnly = true)
    public ResponseEntity<CheckResponseDto> login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        // 사용자 확인
        if (userRepository.findByUsername(username).isEmpty()) {
            return badRequest("등록된 사용자가 없습니다.");
        };

        User user = userRepository.findByUsername(username).get();
        // 비밀번호 확인
        if (!user.getPassword().equals(password)) {
            return badRequest("비밀번혹가 일치하지 않습니다.");
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername()));
        return ResponseEntity.status(HttpStatus.OK).body(new CheckResponseDto(HttpStatus.OK.value(),"로그인이 완료 되었습니다."));
    }
}
