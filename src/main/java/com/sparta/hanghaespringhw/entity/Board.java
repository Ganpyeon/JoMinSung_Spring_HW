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

//    @Column(nullable = false)
//    private String password;

    @ManyToOne
    @JoinColumn(name = "userid")
    private User userId;



    public Board(BoardRequestDto requestDto,User userId) {
        this.contents = requestDto.getContents();
        this.title = requestDto.getTitle();
//        this.password = requestDto.getPassword();
        this.userId = userId;
    }

    public void find(BoardRequestDto boardRequestDto) {
        this.contents = boardRequestDto.getContents();
        this.title = boardRequestDto.getTitle();
    }

    public void update(BoardRequestDto boardRequestDto) {
        this.contents = boardRequestDto.getContents();
        this.title = boardRequestDto.getTitle();
    }
}
