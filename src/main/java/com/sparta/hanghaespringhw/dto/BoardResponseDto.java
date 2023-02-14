package com.sparta.hanghaespringhw.dto;

import com.sparta.hanghaespringhw.entity.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
public class BoardResponseDto { // Entity값은 변형 되지 말아야 하고 게시물에 들어갈 데이터들을 먼저 받고 그다음 게시물을 출력했을때 원하는 데이터만 보여주기 위해 ResponseDto를 만듬
    private Long id;
    private String username;
    private String contents;
    private String title;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;


    public BoardResponseDto(Board board) {
        this.id = board.getId();
        this.username = board.getUserId().getUsername();
        this.contents = board.getContents();
        this.title = board.getTitle();
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
    }

}



