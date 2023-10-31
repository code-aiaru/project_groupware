package spring.project.groupware.academy.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import spring.project.groupware.academy.post.entity.Post;

import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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