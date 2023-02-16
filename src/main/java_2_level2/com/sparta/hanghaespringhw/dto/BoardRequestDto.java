package com.sparta.hanghaespringhw.dto;


import com.sparta.hanghaespringhw.entity.Board;
import lombok.Getter;




@Getter
public class BoardRequestDto { // 게시물에 들어갈 데이터들
    private String contents;
    private String title;
//    private String password;


}