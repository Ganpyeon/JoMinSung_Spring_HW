package com.sparta.hanghaespringhw.service;

import com.sparta.hanghaespringhw.dto.BoardResponseDto;
import com.sparta.hanghaespringhw.dto.CheckResponseDto;
import com.sparta.hanghaespringhw.dto.CommentResponseDto;
import com.sparta.hanghaespringhw.entity.Board;
import com.sparta.hanghaespringhw.entity.Comment;
import com.sparta.hanghaespringhw.entity.Likes;
import com.sparta.hanghaespringhw.entity.User;
import com.sparta.hanghaespringhw.repository.BoardRepository;
import com.sparta.hanghaespringhw.repository.CommentRepository;
import com.sparta.hanghaespringhw.repository.LikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikesService {

    private final LikesRepository likesRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;



    private ResponseEntity<CheckResponseDto> badRequest(String msg){ // ResponseEntity객체를 써서 예외 처리에서쓸 상태 코드와 메세지를 메소드로 전달할수 있게 만들어줌
        return ResponseEntity.badRequest().body(CheckResponseDto.builder()
                .checkmsg(msg) // 메세지를 반환해줌 이.???하는걸 메서드 체인이라고 함 이게 하나 하나다 메서드와 같음
                .checkcode(HttpStatus.BAD_REQUEST.value()) // 상태코드를 지정해줌
                .build());
    }

    @Transactional
    public ResponseEntity<?> likesBoardStatus(Long id, User user){

        if (boardRepository.findById(id).isEmpty()){
            return badRequest("게시물이 없습니다.");
        }
        Board board = boardRepository.findById(id).get();


        Optional<Likes> found = likesRepository.findByUserAndBoard(user,board);
        if (found.isEmpty()){
            board.likeBoardUp();
            Likes likes = Likes.of(board,user);
            likesRepository.save(likes);
        }else {
            board.likeBoardDown();
            likesRepository.delete(found.get());
            likesRepository.flush();
        }

        return ResponseEntity.status(HttpStatus.OK).body(new BoardResponseDto(board));
    }

    @Transactional
    public ResponseEntity<?> likesCommentStatus(Long id, User user){

        if (commentRepository.findById(id).isEmpty()){
            return badRequest("댓글이 없습니다.");
        }
        Comment comment = commentRepository.findById(id).get();

        Optional<Likes> found = likesRepository.findByUserAndComment(user,comment);
        if (found.isEmpty()){
            comment.likeCommentUp();
            Likes likes = Likes.of(comment,user);
            likesRepository.save(likes);
        }else {
            comment.likeCommentDown();
            likesRepository.delete(found.get());
            likesRepository.flush();
        }

        return ResponseEntity.status(HttpStatus.OK).body(new CommentResponseDto(comment));
    }
}
