package spring.project.groupware.academy.post.dto;

import lombok.Getter;
import spring.project.groupware.academy.post.entity.Post;


@Getter
public class PostResponseDTO {

    private Long id;
    private String title;
    private String content;
    private String pw;
    private String writer;
    public PostResponseDTO(Post post) {
        this.id = post.getId();
        this.pw = post.getPw();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.writer = post.getWriter();
    }

}