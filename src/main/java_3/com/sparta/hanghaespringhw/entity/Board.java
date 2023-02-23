package com.sparta.hanghaespringhw.entity;

import com.sparta.hanghaespringhw.dto.BoardRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Board extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // 주문을 받을때마다 자동으로 식별할수 있는 고유 번호를 만들어줌

   /* @Column(nullable = false)
    private String username;*/

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private String title;

    /*@ManyToOne
    @JoinColumn(name = "userid")
    private User userId; // user와 board에 연관관계를 해서 user에 있는 변수들을 찾아서 쓸수있게 만들어줌
    //오류
    // "Parameter value [1] did not match expected type [com.sparta.hanghaespringhw.entity.User (n/a)]; nested exception is java.lang.IllegalArgumentException: Parameter value [1] did not match expected type [com.sparta.hanghaespringhw.entity.User (n/a)]"

    // 오늘 물어볼거 : 해답 - DB에서 user.getId를 가져올때 User 변수명을 userId로 바꿔서 user값에다가 User pk값을 넣으려 해서 오류가 걸렸음
    // 스프링 컨테이너에 User가 user로 이미 들어가 있어서 userId로 하면 오류뜨는건가요
    //이미 bean으로 user라는 이름으로 들어가서
    //리플렉션할때 같은 이름으로 가져오니까 문제가 된다.*/

    @ManyToOne
    @JoinColumn(name = "userid")
    private User user; // user와 board에 연관관계를 해서 user에 있는 변수들을 찾아서 쓸수있게 만들어줌

    @OneToMany(mappedBy = "board",cascade = CascadeType.ALL)
    private List<Comment> commentList = new ArrayList<>();






    public Board(BoardRequestDto requestDto,User userId) {
        this.contents = requestDto.getContents();
        this.title = requestDto.getTitle();
        this.user = userId;
    }

    public void find(BoardRequestDto boardRequestDto) {
        this.contents = boardRequestDto.getContents();
        this.title = boardRequestDto.getTitle();
    }

    public void update(BoardRequestDto boardRequestDto) {
        this.contents = boardRequestDto.getContents();
        this.title = boardRequestDto.getTitle();
    }
}
