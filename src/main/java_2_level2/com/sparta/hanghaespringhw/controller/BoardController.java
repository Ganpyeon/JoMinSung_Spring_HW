package com.sparta.hanghaespringhw.controller;

import com.sparta.hanghaespringhw.dto.BoardRequestDto;
import com.sparta.hanghaespringhw.dto.BoardResponseDto;
import com.sparta.hanghaespringhw.entity.Board;
import com.sparta.hanghaespringhw.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;


    @PostMapping("/api/boards")
    public BoardResponseDto createBoard(@RequestBody BoardRequestDto requestDto, HttpServletRequest request) { // 웹에서 DB로 게시물 저장을 위한 코드
        return boardService.createBoard(requestDto, request);
    }

    @GetMapping("/api/boards")
    public List<BoardResponseDto> getboard() { // 게시물을 조회하는 코드인데 이것도 List<Board>로 하면 원하지 않는 데이터까지 전부 보여줌 그래서 BoardResponseDto리스트를 하나 만들어서 거기다가 넣어주고 원하는 데이터를 뽑아줌
        return boardService.getboard();
    }

    @GetMapping("/api/boards/{id}")
    public BoardResponseDto findBoard(@PathVariable Long id,@RequestBody BoardRequestDto requestDto)  { // 선택한 게시물을 찾아주는 코드이며 고유 id를 받고 똑같은 게시물을 찾아줌
        return boardService.find(id, requestDto);
    }

    @PutMapping("/api/boards/{id}")
    public BoardResponseDto updateBoard(@PathVariable Long id, @RequestBody BoardRequestDto requestDto, HttpServletRequest request)  { // 게시물 수정코드 이다
        return boardService.update(id, requestDto, request);
    }


    @DeleteMapping("/api/boards/{id}")
    public String deleteBoard(@PathVariable Long id, @RequestBody BoardRequestDto requestDto, HttpServletRequest request)  {
        return boardService.deleteBoard(id, requestDto, request);
    }
}
