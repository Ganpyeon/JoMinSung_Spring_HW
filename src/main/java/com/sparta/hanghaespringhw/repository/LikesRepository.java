package com.sparta.hanghaespringhw.repository;

import com.sparta.hanghaespringhw.entity.Board;
import com.sparta.hanghaespringhw.entity.Comment;
import com.sparta.hanghaespringhw.entity.Likes;
import com.sparta.hanghaespringhw.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {

    Optional<Likes> findByUserAndBoard(User user, Board board);
    Optional<Likes> findByUserAndComment(User user, Comment comment);
}
