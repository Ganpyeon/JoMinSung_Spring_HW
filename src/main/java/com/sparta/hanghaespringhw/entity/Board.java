package com.sparta.hanghaespringhw.entity;

import com.sparta.hanghaespringhw.dto.BoardRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Board extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // 주문을 받을때마다 자동으로 식별할수 있는 고유 번호를 만들어줌

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String password;


    public Board(BoardRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.contents = requestDto.getContents();
        this.title = requestDto.getTitle();
        this.password = requestDto.getPassword();
    }

    public void find(BoardRequestDto boardRequestDto) {
        this.username = boardRequestDto.getUsername();
        this.contents = boardRequestDto.getContents();
        this.title = boardRequestDto.getTitle();
    }
}
