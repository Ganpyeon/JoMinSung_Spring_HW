package com.sparta.hanghaespringhw.service;

import com.sparta.hanghaespringhw.dto.CheckResponseDto;
import com.sparta.hanghaespringhw.dto.LoginRequestDto;
import com.sparta.hanghaespringhw.dto.SignupRequestDto;
import com.sparta.hanghaespringhw.entity.User;
import com.sparta.hanghaespringhw.entity.UserRoleEnum;
import com.sparta.hanghaespringhw.jwt.JwtUtil;
import com.sparta.hanghaespringhw.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class    UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    private final PasswordEncoder passwordEncoder;

    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    private ResponseEntity<CheckResponseDto> badRequest(String msg){ // ResponseEntity객체를 써서 예외 처리에서쓸 상태 코드와 메세지를 메소드로 전달할수 있게 만들어줌
        return ResponseEntity.badRequest().body(CheckResponseDto.builder()
                .checkmsg(msg) // 메세지를 반환해줌 이.???하는걸 메서드 체인이라고 함 이게 하나 하나다 메서드와 같음
                .checkcode(HttpStatus.BAD_REQUEST.value()) // 상태코드를 지정해줌
                .build());
    }

    @Transactional
    public ResponseEntity<CheckResponseDto> signup(SignupRequestDto signupRequestDto) { // 회원 가입
        String username = signupRequestDto.getUsername();
        String password = passwordEncoder.encode(signupRequestDto.getPassword()); // 비밀번호를 암호화 해주기 위해 encoder를 사용하였다.

        // 회원 중복 확인
        Optional<User> found = userRepository.findByUsername(signupRequestDto.getUsername());
        if (found.isPresent()) { // user리스트에서 username을 찾아서 isPresent로 중복이 있는지 확인을 해서 있으면 중복된 사용자라고 넘겨줌
            return badRequest("중복된 사용자가 존재합니다.");
        }

        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (signupRequestDto.isAdmin()) { // 여기서 관리자 권한을 넣을건지 조건으로 넣어줌
            if (!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) { // 위에서 관리자 권한을 넣는다고 true값이 되면 adminToken을 넣고 일치하는지 확인한다.
                return badRequest("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }



        User user = new User(username, password, role); // 매개변수로 회원 가입에 들어갈 데이터들을 받아서 user에 저장해줌
        userRepository.save(user); // 받은 데이터들을 user리스트에 저장해줌
        return ResponseEntity.status(HttpStatus.OK).body(new CheckResponseDto(HttpStatus.OK.value(),"회원가입이 완료 되었습니다.")); // 회원 가입이 성공했다는 상태 코드와 메세지를 전달 해주기 위해 ResponseEntity를 반환값으로 주었으며 성공 했을때 메세지와 상태코드를 반환해줌
    }

    @Transactional(readOnly = true)
    public ResponseEntity<CheckResponseDto> login(LoginRequestDto loginRequestDto, HttpServletResponse response) { // HttpServletRespons는 비어있는 객치인데 여기에 로그인 정보를 저장해서 응담을 보내가지고 고유jwt token을 받아오기 위해 사용을함
        String username = loginRequestDto.getUsername(); // 회원 가입하고나서 받을 username과password값
        String password = loginRequestDto.getPassword();

        // 사용자 확인
        if (userRepository.findByUsername(username).isEmpty()) { // 회원 가입하고 저장된 user리스트에서 username를 받아왔을때 그 username이 없을때의 예외처리
            return badRequest("등록된 사용자가 없습니다.");
        }

        User user = userRepository.findByUsername(username).get();// user 리스트에 있는 username를 찾아줘서 비어있는 객체인 user에 담아줌
        // 비밀번호 확인
        if (!passwordEncoder.matches(password, user.getPassword())) {// 회원 가입하고 저장된 user리스트에서 username를 받아오고 username과 같은 password와 일치하지 않았을때의 예외처리
            return badRequest("비밀번호가 일치하지 않습니다.");
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole()));// 로그인 했을때 그 유저 전용 JWT TOKEN을 받아오기 위한 코드
        return ResponseEntity.status(HttpStatus.OK).body(new CheckResponseDto(HttpStatus.OK.value(),"로그인이 완료 되었습니다.")); // 로그인 했을때의 상태 코드와 메세지를 전달해주기 위한 코드
    }
}
