package com.sparta.hanghaespringhw.service;


import com.sparta.hanghaespringhw.dto.CheckResponseDto;
import com.sparta.hanghaespringhw.entity.Board;
import com.sparta.hanghaespringhw.dto.BoardRequestDto;
import com.sparta.hanghaespringhw.dto.BoardResponseDto;
import com.sparta.hanghaespringhw.entity.User;
import com.sparta.hanghaespringhw.entity.UserRoleEnum;
import com.sparta.hanghaespringhw.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;



    @Transactional
    public BoardResponseDto createBoard(BoardRequestDto requestDto, User user) { // 게시물 등록하기



            // 요청받은 DTO 로 DB에 저장할 객체 만들기
            Board board = boardRepository.saveAndFlush(new Board(requestDto, user));

            return new BoardResponseDto(board);

            /*Board board = new Board(requestDto); // Board 에다가 requestDto의 데이터를 넣어주기 위한 코드
        boardRepository.saveAndFlush(board); // 그다음 Repository에 있는 리스트에 작성자,제목,작성 내용, 패스워드, 작성날짜, 고유번호를 넣어줌
        return board;*/
    }


    @Transactional(readOnly = true)
    public List<BoardResponseDto> getboard() {
        List<Board> boards = boardRepository.findAllByOrderByModifiedAtDesc(); // 이대로 반환하면 board에 있는 값이 나와서 비밀번호 값도 나와버림
        List<BoardResponseDto> responseDtos = new ArrayList<>(); // 그래서 리스트를 하나 만들어서 password를 뺀 값을 리스트에 넣어줌
        for (Board board : boards) { // for (a : b) 세미 콜론은 둘다 배열일때 b에 있는 값을 차례대로 a에 넣는데 b가 a에 넣을값이 없을때까지 반복문을 돌린다 여기서 a는 공간이 빈 배열이다.
            responseDtos.add(new BoardResponseDto(board)); // 리스트에 BoardResponseDto에 board에 있는 모든값을 넣어서 출력하고 싶은것만 출력함
        }
        return responseDtos;
    }

    @Transactional
    public BoardResponseDto find(Long id, BoardRequestDto requestDto) { // 선택한 게시물 찾기

        Board board = boardRepository.findById(id).orElseThrow( // 게시물의 고유id를 먼저 찾아서
                () -> new IllegalArgumentException("게시물이 존재하지 않습니다.") // 아이디가 빈칸이면 존재하지 않습니다를 표기해줌
        );

            // 요청받은 DTO 로 DB에 저장할 객체 만들기
            board.find(requestDto);

            return new BoardResponseDto(board);
    }

    @Transactional
    public BoardResponseDto update(Long id, BoardRequestDto requestDto, User user) { // 게시물 수정하는 코드


        Board board = new Board();

        roleAuthority(id, user, board);
       /* // 사용자 권한 가져와서 ADMIN 이면 전체 수정,삭제 USER 면 본인의 게시글만 수정,삭제
        UserRoleEnum userRoleEnum = user.getRole();

        Board board;


        if (userRoleEnum == UserRoleEnum.USER) { // 사용자 권한이 USER일 경우
            board = boardRepository.findByIdAndUserId(id, user.getId()).orElseThrow( // 고유 ID를 찾아줘서 게시물이 있는지 확인을 해줌
                    () -> new IllegalArgumentException("게시물이 존재하지 않습거나 회원님의 게시물이 아닙니다.")
            );
        } else { // 사용자 권한이 ADMIN일 경우
            board = boardRepository.findById(id).orElseThrow( // 고유 ID를 찾아줘서 게시물이 있는지 확인을 해줌
                    () -> new IllegalArgumentException("게시물이 존재하지 않습니다.")
            );
        }*/



            // 요청받은 DTO 로 DB에 저장할 객체 만들기
            board.update(requestDto);

            return new BoardResponseDto(board);

    }

    @Transactional
    public ResponseEntity<CheckResponseDto> deleteBoard(Long id,User user)  { // 게시물 삭제

        Board board = new Board();

        roleAuthority(id, user, board);

        /*// 사용자 권한 가져와서 ADMIN 이면 전체 수정,삭제 USER 면 본인의 게시글만 수정,삭제
        UserRoleEnum userRoleEnum = user.getRole();

        Board board;


        if (userRoleEnum == UserRoleEnum.USER) {
            board = boardRepository.findByIdAndUserId(id, user.getId()).orElseThrow( // 고유 ID를 찾아줘서 게시물이 있는지 확인을 해줌
                () -> new IllegalArgumentException("게시물이 존재하지 않습거나 회원님의 게시물이 아닙니다.")
        );
            // 사용자 권한이 USER일 경우
        } else {
            board = boardRepository.findById(id).orElseThrow( // 고유 ID를 찾아줘서 게시물이 있는지 확인을 해줌
                () -> new IllegalArgumentException("게시물이 존재하지 않습니다.")
        );
        }*/



        boardRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new CheckResponseDto(HttpStatus.OK.value(),"게시글이 삭제가 되었습니다."));
    }

    private void roleAuthority(Long id, User user, Board board){ // 사용자 권한을 메서드로 만들어주었음
        // 사용자 권한 가져와서 ADMIN 이면 전체 수정,삭제 USER 면 본인의 게시글만 수정,삭제
        UserRoleEnum userRoleEnum = user.getRole();



        if (userRoleEnum == UserRoleEnum.USER) {
            board = boardRepository.findByIdAndUserId(id, user.getId()).orElseThrow( // 고유 ID를 찾아줘서 게시물이 있는지 확인을 해줌
                    () -> new IllegalArgumentException("게시물이 존재하지 않습거나 회원님의 게시물이 아닙니다.")
            );
            // 사용자 권한이 USER일 경우
        } else {
            board = boardRepository.findById(id).orElseThrow( // 고유 ID를 찾아줘서 게시물이 있는지 확인을 해줌
                    () -> new IllegalArgumentException("게시물이 존재하지 않습니다.")
            );
        }
    }

}

