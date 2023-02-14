package com.sparta.hanghaespringhw.repository;

import com.sparta.hanghaespringhw.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> { // DB와 직접 연결해주기 위한 코드로 알고있음
    List<Board> findAllByOrderByModifiedAtDesc(); // 게시글 생성시 내림차순 정렬
}
