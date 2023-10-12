package spring.project.groupware.academy.board.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Board")
public class BoardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;
    @Column(nullable = false)
    private String writer;
    @Column(nullable = false)
    private String boardPw;


    public BoardEntity(String title, String content,String writer,String boardPw) {
        this.title=title;
        this.content=content;
        this.writer=writer;
        this.boardPw=boardPw;
    }

    public BoardEntity(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
