package com.sparta.hanghaespringhw.repository;

import com.sparta.hanghaespringhw.entity.Board;
import com.sparta.hanghaespringhw.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findByIdAndUserId(Long id, Long userId);

}
