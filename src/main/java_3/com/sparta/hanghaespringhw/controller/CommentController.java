package com.sparta.hanghaespringhw.controller;

import com.sparta.hanghaespringhw.dto.CheckResponseDto;
import com.sparta.hanghaespringhw.dto.CommentRequestDto;
import com.sparta.hanghaespringhw.dto.CommentResponseDto;
import com.sparta.hanghaespringhw.security.UserDetailsImpl;
import com.sparta.hanghaespringhw.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/api/comments/{id}")
    public ResponseEntity<?> createComment(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.createComment(id, commentRequestDto, userDetails.getUser());
    }

    @PutMapping("/api/comments/{id}")
    public ResponseEntity<?> updateComment(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.updateComment(id, commentRequestDto, userDetails.getUser());
    }

    @DeleteMapping ("/api/comments/{id}")
    public ResponseEntity<CheckResponseDto> deleteComment(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.deleteComment(id, userDetails.getUser());
    }
}
