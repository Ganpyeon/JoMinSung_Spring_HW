package com.sparta.hanghaespringhw.entity;

import com.sparta.hanghaespringhw.dto.SignupRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity(name = "users")
public class User { // 회원을 만들기 위한 Entity객체 이며 회원 가입할때 필요한 데이터들 이다.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // nullable: null 허용 여부
    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    // 게시글 과 작성자 연관 관계 한 유저가 여러개의 게시글을 작성할수 있으면 여러개의 게시글을 조회 할수 있으니 ONE TO MANY
    /*@OneToMany
    List<Board> boards = new ArrayList<>();*/

    public User(SignupRequestDto signupRequestDto){
        this.username = signupRequestDto.getUsername();
        this.password = signupRequestDto.getPassword();
    }
}
