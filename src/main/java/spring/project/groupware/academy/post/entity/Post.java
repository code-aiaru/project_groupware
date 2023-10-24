package spring.project.groupware.academy.post.entity;

import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String title;
    @Column(nullable = false)
    private String pw;
    @Column(nullable = false, length = 3000)
    private String content;
    @Column(nullable = false)
    private String writer;

    @Builder
    public Post(String title, String content ,String pw ,String writer) {
        this.title = title;
        this.content = content;
        this.pw = pw;
        this.writer = writer;
    }

    public boolean verifyPassword(String Password) {
        return getPw().equals(Password);

    }
}