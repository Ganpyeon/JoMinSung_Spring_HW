package com.sparta.hanghaespringhw.service;


import com.sparta.hanghaespringhw.entity.Board;
import com.sparta.hanghaespringhw.dto.BoardRequestDto;
import com.sparta.hanghaespringhw.dto.BoardResponseDto;
import com.sparta.hanghaespringhw.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public Board createBoard(BoardRequestDto requestDto) { // 게시물 등록하기
        Board board = new Board(requestDto); // Board 에다가 requestDto의 데이터를 넣어주기 위한 코드
        boardRepository.save(board); // 그다음 Repository에 있는 리스트에 작성자,제목,작성 내용, 패스워드, 작성날짜, 고유번호를 넣어줌
        return board;
    }


    @Transactional(readOnly = true)
    public List<BoardResponseDto> getboard() {
        List<Board> boards = boardRepository.findAllByOrderByModifiedAtDesc(); // 이대로 반환하면 board에 있는 값이 나와서 비밀번호 값도 나와버림
        List<BoardResponseDto> responseDtos = new ArrayList<>(); // 그래서 리스트를 하나 만들어서 password를 뺀 값을 리스트에 넣어줌
        for (Board board : boards) { // for ( a : b) 세미 콜론은 둘다 배열일때 b에 있는 값을 차례대로 a에 넣는데 b가 a에 넣을값이 없을때까지 반복문을 돌린다
            responseDtos.add(new BoardResponseDto(board)); // 리스트에 BoardResponseDto에 board에 있는 모든값을 넣어서 출력하고 싶은것만 출력함
        }
        return responseDtos;
    }

    @Transactional
    public BoardResponseDto find(Long id) { // 선택한 게시물 찾기
        Board board = boardRepository.findById(id).orElseThrow( // 고유번호 Id로 선택한 게시물을 찾음
                () -> new IllegalArgumentException("게시물 없음") // 아이디가 빈칸이면 게시물 없음이라고 띄워줌
        );
        return new BoardResponseDto(board);// 찾으면 그 고유번호에 맞는 데이터를 가져와줌
    }

    @Transactional
    public Board update(Long id, BoardRequestDto requestDto) throws Exception {
        Board board = boardRepository.findById(id).orElseThrow( // 게시물 수정하기 코드
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.") // 아이디가 빈칸이면 존재하지 않습니다를 표기해줌
        );
        if (!requestDto.getPassword().equals(requestDto.getPassword()))
            throw new Exception("비밀번호가 다릅니다.");// 비밀번호를 비교하여 다르면 비밀번호가 다르다고 표기해줌
        board.update(requestDto); // id와 비밀번호가 모두 일치하면 데이터를 수정해준다,
        return board;
    }

    @Transactional
    public String deleteBoard(Long id, BoardRequestDto requestDto) throws Exception {
        Board board = boardRepository.findById(id).orElseThrow( // 게시물을 삭제하는 코드
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        if (!requestDto.getPassword().equals(requestDto.getPassword()))
            throw new Exception("비밀번호가 다릅니다.");
        boardRepository.deleteById(id);
        return ("게시글이 삭제 되었습니다.");
    }

}
