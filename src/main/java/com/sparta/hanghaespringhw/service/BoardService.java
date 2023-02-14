package com.sparta.hanghaespringhw.service;


import com.sparta.hanghaespringhw.entity.Board;
import com.sparta.hanghaespringhw.dto.BoardRequestDto;
import com.sparta.hanghaespringhw.dto.BoardResponseDto;
import com.sparta.hanghaespringhw.entity.User;
import com.sparta.hanghaespringhw.jwt.JwtUtil;
import com.sparta.hanghaespringhw.repository.BoardRepository;
import com.sparta.hanghaespringhw.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public BoardResponseDto createBoard(BoardRequestDto requestDto ,HttpServletRequest request) { // 게시물 등록하기

        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는 경우에만 관심상품 최저가 업데이트 가능
        if (token != null) {
            // Token 검증
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            // 요청받은 DTO 로 DB에 저장할 객체 만들기
            Board board = boardRepository.saveAndFlush(new Board(requestDto, user));

            return new BoardResponseDto(board);
        } else {
            return null;
        }

            /*Board board = new Board(requestDto); // Board 에다가 requestDto의 데이터를 넣어주기 위한 코드
        boardRepository.saveAndFlush(board); // 그다음 Repository에 있는 리스트에 작성자,제목,작성 내용, 패스워드, 작성날짜, 고유번호를 넣어줌
        return board;*/
    }


    @Transactional(readOnly = true)
    public List<BoardResponseDto> getboard() {
        List<Board> boards = boardRepository.findAllByOrderByModifiedAtDesc(); // 이대로 반환하면 board에 있는 값이 나와서 비밀번호 값도 나와버림
        List<BoardResponseDto> responseDtos = new ArrayList<>(); // 그래서 리스트를 하나 만들어서 password를 뺀 값을 리스트에 넣어줌
        for (Board board : boards) { // for (a : b) 세미 콜론은 둘다 배열일때 b에 있는 값을 차례대로 a에 넣는데 b가 a에 넣을값이 없을때까지 반복문을 돌린다
            responseDtos.add(new BoardResponseDto(board)); // 리스트에 BoardResponseDto에 board에 있는 모든값을 넣어서 출력하고 싶은것만 출력함
        }
        return responseDtos;
    }

    @Transactional
    public BoardResponseDto find(Long id, BoardRequestDto requestDto , HttpServletRequest request) { // 선택한 게시물 찾기

        Board board = boardRepository.findById(id).orElseThrow( // id를 먼저 찾는다
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.") // 아이디가 빈칸이면 존재하지 않습니다를 표기해줌
        );

        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는 경우에만 관심상품 최저가 업데이트 가능
        if (token != null) {
            // Token 검증
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            // 요청받은 DTO 로 DB에 저장할 객체 만들기
            board.find(requestDto);
            new BoardResponseDto(board);

            return new BoardResponseDto(board);
        } else {
            return null;
        }
    }

    @Transactional
    public BoardResponseDto update(Long id, BoardRequestDto requestDto, HttpServletRequest request) {
        Board board = boardRepository.findById(id).orElseThrow( // 게시물 수정하기 코드
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.") // 아이디가 빈칸이면 존재하지 않습니다를 표기해줌
        );

        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는 경우에만 관심상품 최저가 업데이트 가능
        if (token != null) {
            // Token 검증
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            // 요청받은 DTO 로 DB에 저장할 객체 만들기
            board.update(requestDto);

            return new BoardResponseDto(board);
        } else {
            return null;
        }

    }

    @Transactional
    public String deleteBoard(Long id, BoardRequestDto requestDto, HttpServletRequest request)  {
        Board board = boardRepository.findById(id).orElseThrow( // 게시물을 삭제하는 코드
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는 경우에만 관심상품 최저가 업데이트 가능
        if (token != null) {
            // Token 검증
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            boardRepository.deleteById(id);
            return ("게시글이 삭제 되었습니다.");
        }else {
                return null;
            }
    }

}
