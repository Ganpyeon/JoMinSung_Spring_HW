package com.sparta.hanghaespringhw.repository;

import com.sparta.hanghaespringhw.entity.Board;
import com.sparta.hanghaespringhw.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> { // DB와 직접 연결해주기 위한 코드로 알고있음
    List<Board> findAllByOrderByModifiedAtDesc(); // 게시글 생성시 내림차순 정렬
    Optional<Board> findByIdAndUserId(Long id, Long userId);
}
