package spring.project.groupware.academy.board.entity;


import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "notice")
public class NoticeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String writer;
    @Column(nullable = false)
    private String content;


    public NoticeEntity(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public NoticeEntity(String title, String content, String writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
    }
}
