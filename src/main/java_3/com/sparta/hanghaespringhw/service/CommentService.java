package com.sparta.hanghaespringhw.service;

import com.sparta.hanghaespringhw.dto.CheckResponseDto;
import com.sparta.hanghaespringhw.dto.CommentRequestDto;
import com.sparta.hanghaespringhw.dto.CommentResponseDto;
import com.sparta.hanghaespringhw.entity.Board;
import com.sparta.hanghaespringhw.entity.Comment;
import com.sparta.hanghaespringhw.entity.User;
import com.sparta.hanghaespringhw.entity.UserRoleEnum;
import com.sparta.hanghaespringhw.repository.BoardRepository;
import com.sparta.hanghaespringhw.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;




    private ResponseEntity<CheckResponseDto> badRequest(String msg){ // ResponseEntity객체를 써서 예외 처리에서쓸 상태 코드와 메세지를 메소드로 전달할수 있게 만들어줌
        return ResponseEntity.badRequest().body(CheckResponseDto.builder()
                .checkmsg(msg) // 메세지를 반환해줌 이.???하는걸 메서드 체인이라고 함 이게 하나 하나다 메서드와 같음
                .checkcode(HttpStatus.BAD_REQUEST.value()) // 상태코드를 지정해줌
                .build());
    }
    @Transactional
    public ResponseEntity<?> createComment(Long id, CommentRequestDto commentRequestDto, User user) {



        if (boardRepository.findById(id).isEmpty()){
            return badRequest("게시물이 없습니다.");
        }
        Board board = boardRepository.findById(id).get();

        Comment comment = commentRepository.saveAndFlush(new Comment(commentRequestDto, user, board));

        return ResponseEntity.status(HttpStatus.OK).body(new CommentResponseDto(comment));
    }

    @Transactional
    public ResponseEntity<?> updateComment(Long id, CommentRequestDto commentRequestDto, User user) {

        UserRoleEnum userRoleEnum = user.getRole();

        Comment comment = new Comment();


        if (userRoleEnum == UserRoleEnum.USER) {// 사용자 권한이 user일 경우
            comment = commentRepository.findByIdAndUserId(id, user.getId()).get();
            if (comment == null){
                return badRequest("게시물이 존재하지 않거나 회원님의 게시물이 아닙니다.");
            }
        } else {
            if (commentRepository.findById(id).isEmpty()){
                return badRequest("게시물이 존재하지 않습니다.");
            }
            comment = commentRepository.findById(id).get();
        }

        comment.commentUpdate(commentRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(new CommentResponseDto(comment));
    }

    @Transactional
    public ResponseEntity<CheckResponseDto> deleteComment(Long id, User user) {

        UserRoleEnum userRoleEnum = user.getRole();

        Comment comment = new Comment();


        if (userRoleEnum == UserRoleEnum.USER) {// 사용자 권한이 user일 경우
            comment = commentRepository.findByIdAndUserId(id, user.getId()).get();
            if (comment == null){
                return badRequest("게시물이 존재하지 않거나 회원님의 게시물이 아닙니다.");
            }
        } else {
            if (commentRepository.findById(id).isEmpty()){
                return badRequest("게시물이 존재하지 않습니다.");
            }
            comment = commentRepository.findById(id).get();
        }


        commentRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK.value()).body(new CheckResponseDto(HttpStatus.OK.value(),"댓글 삭제 완료"));
    }

}
