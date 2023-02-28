package com.sparta.hanghaespringhw.controller;

import com.sparta.hanghaespringhw.security.UserDetailsImpl;
import com.sparta.hanghaespringhw.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LikesController {

    private final LikesService likesService;

    @PostMapping("/api/likes/boards/{id}")
    public ResponseEntity<?> likesBoardStatus(@PathVariable Long id,@AuthenticationPrincipal UserDetailsImpl userDetails){
        return likesService.likesBoardStatus(id, userDetails.getUser());
    }

    @PostMapping("/api/likes/comments/{id}")
    public ResponseEntity<?> likesCommentStatus(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return likesService.likesCommentStatus(id, userDetails.getUser());
    }
}
