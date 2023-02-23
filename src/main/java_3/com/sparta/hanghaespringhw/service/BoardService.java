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

    private ResponseEntity<CheckResponseDto> badRequest(String msg){ // ResponseEntity객체를 써서 예외 처리에서쓸 상태 코드와 메세지를 메소드로 전달할수 있게 만들어줌
        return ResponseEntity.badRequest().body(CheckResponseDto.builder()
                .checkmsg(msg) // 메세지를 반환해줌 이.???하는걸 메서드 체인이라고 함 이게 하나 하나다 메서드와 같음
                .checkcode(HttpStatus.BAD_REQUEST.value()) // 상태코드를 지정해줌
                .build());
    }

    @Transactional
    public BoardResponseDto createBoard(BoardRequestDto requestDto, User user) { // 게시물 등록하기

            // 요청받은 DTO 로 DB에 저장할 객체 만들기
            Board board = boardRepository.saveAndFlush(new Board(requestDto, user));

            return new BoardResponseDto(board);
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
    public ResponseEntity<?> find(Long id, BoardRequestDto requestDto) { // 선택한 게시물 찾기

       if (boardRepository.findById(id).isEmpty()){
           return badRequest("게시물이 없습니다.");
       }

        Board board = boardRepository.findById(id).get();
            // 요청받은 DTO 로 DB에 저장할 객체 만들기
            board.find(requestDto);

            return ResponseEntity.status(HttpStatus.OK).body(new BoardResponseDto(board));
    }

    @Transactional
    public ResponseEntity<?> update(Long id, BoardRequestDto requestDto, User user) { // 게시물 수정하는 코드


        UserRoleEnum userRoleEnum = user.getRole();

        Board board = new Board();

        if (userRoleEnum == UserRoleEnum.USER) {// 사용자 권한이 user일 경우
            board = boardRepository.findByIdAndUserId(id, user.getId()).get();
            if (board == null){
                return badRequest("게시물이 존재하지 않거나 회원님의 게시물이 아닙니다.");
            }
        } else {
            if (boardRepository.findById(id).isEmpty()){
                return badRequest("게시물이 존재하지 않습니다.");
            }
            board = boardRepository.findById(id).get();
        }



            // 요청받은 DTO 로 DB에 저장할 객체 만들기
            board.update(requestDto);

            return ResponseEntity.status(HttpStatus.OK).body(new BoardResponseDto(board));

    }

    @Transactional
    public ResponseEntity<CheckResponseDto> deleteBoard(Long id,User user)  { // 게시물 삭제

        UserRoleEnum userRoleEnum = user.getRole();

        Board board = new Board();

        if (userRoleEnum == UserRoleEnum.USER) {// 사용자 권한이 user일 경우
            board = boardRepository.findByIdAndUserId(id, user.getId()).get();
            if (board == null){
                return badRequest("게시물이 존재하지 않거나 회원님의 게시물이 아닙니다.");
            }
        } else {
            if (boardRepository.findById(id).isEmpty()){
                return badRequest("게시물이 존재하지 않습니다.");
            }
            board = boardRepository.findById(id).get();
        }



        boardRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new CheckResponseDto(HttpStatus.OK.value(),"게시글이 삭제가 되었습니다."));
    }

}

