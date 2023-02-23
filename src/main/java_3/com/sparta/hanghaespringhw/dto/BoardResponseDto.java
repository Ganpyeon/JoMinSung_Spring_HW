package com.sparta.hanghaespringhw.dto;

import com.sparta.hanghaespringhw.entity.Board;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class BoardResponseDto { // Entity값은 변형 되지 말아야 하고 게시물에 들어갈 데이터들을 먼저 받고 그다음 게시물을 출력했을때 원하는 데이터만 보여주기 위해 ResponseDto를 만듬
    private Long id;
    private String username;
    private String contents;
    private String title;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<CommentResponseDto> commentList;


    public BoardResponseDto(Board board) {
        this.id = board.getId();
        this.username = board.getUser().getUsername();//boardEntity에서 user의 데이터를 받아왔다. 원래는 board에서 username을 받았다면 이번에는 로그인한 유저에 있는 username을 가져와야되서
        this.contents = board.getContents();
        this.title = board.getTitle();
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
        this.commentList = board.getCommentList().stream().map(CommentResponseDto::new).collect(Collectors.toList());
    }

}



