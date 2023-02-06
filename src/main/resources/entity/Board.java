package entity;

import com.sparta.hanghaespringhw.dto.BoardRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;


import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor

public class Board extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String password;


    /*public Board(String title,String contents, String username, String password){
        this.title = title;
        this.contents = contents;
        this.username = username;
        this.password = password;
    }*/

    public Board(BoardRequestDto requestDto){ // 게시물에 들어갈 데이터들 작성자,제목,작성 내용, 패스워드
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.username = requestDto.getUsername();
        this.password = requestDto.getPassword();
    }

    public void find(BoardRequestDto boardRequestDto) {
        this.username = boardRequestDto.getUsername();
        this.contents = boardRequestDto.getContents();
        this.title = boardRequestDto.getTitle();
    }

    public void update(BoardRequestDto boardRequestDto) {
        this.title = boardRequestDto.getTitle();
        this.contents = boardRequestDto.getContents();
        this.username = boardRequestDto.getUsername();
    }
}
