package spring.project.groupware.academy.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import spring.project.groupware.academy.post.entity.Notice;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NoticeResponseDTO {

    private Long id;
    private String title;
    private String content;
    private String pw;
    private String writer;
    public NoticeResponseDTO(Notice notice) {
        this.id = notice.getId();
        this.pw = notice.getPw();
        this.title = notice.getTitle();
        this.content = notice.getContent();
        this.writer = notice.getWriter();
    }



}