package com.sparta.hanghaespringhw.entity;

import com.sparta.hanghaespringhw.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String comment;

    @ManyToOne
    @JoinColumn(name = "userid")
    private User user;

    @ManyToOne
    @JoinColumn(name = "boardid")
    private Board board;


    public Comment(CommentRequestDto commentRequestDto, User userId, Board boardId){
        this.comment = commentRequestDto.getComment();
        this.user = userId;
        this.board = boardId;
    }

    public void commentUpdate(CommentRequestDto commentRequestDto){
        this.comment = commentRequestDto.getComment();
    }


}
