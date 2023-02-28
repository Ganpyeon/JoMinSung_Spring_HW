package com.sparta.hanghaespringhw.entity;

import com.sparta.hanghaespringhw.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
    private List<Likes> likesList = new ArrayList<>();

    private Integer likeCount;


    public Comment(CommentRequestDto commentRequestDto, User userId, Board boardId){
        this.comment = commentRequestDto.getComment();
        this.user = userId;
        this.board = boardId;
        this.likeCount = 0;
    }

    public void commentUpdate(CommentRequestDto commentRequestDto){
        this.comment = commentRequestDto.getComment();
    }

    public void likeCommentUp(){
        this.likeCount += 1;
    }

    public void likeCommentDown(){
        this.likeCount -= 1;
    }


}
