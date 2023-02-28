package com.sparta.hanghaespringhw.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Likes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "userid")
    private User user;

    @ManyToOne
    @JoinColumn(name = "boardid")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "commentid")
    private Comment comment;

    @Builder
    public Likes(User user, Board board, Comment comment){
        this.user = user;
        this.comment = comment;
        this.board = board;
    }
    public static Likes of(Board board, User user){
        Likes likes = Likes.builder()
                .board(board)
                .user(user)
                .build();
        board.getLikesList().add(likes);
        return likes;
    }

    public static Likes of(Comment comment, User user){
        Likes likes = Likes.builder()
                .comment(comment)
                .user(user)
                .build();
        comment.getLikesList().add(likes);
        return likes;
    }


}
