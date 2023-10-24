package spring.project.groupware.academy.post.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.project.groupware.academy.post.entity.Notice;
import spring.project.groupware.academy.post.entity.Post;


@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoticeRequestDTO {

    private String title;
    private String content;
    private String pw;
    private String writer;
    public Notice toEntity() {
        return Notice.builder()
                .title(title)
                .content(content)
                .pw(pw)
                .writer(writer)
                .build();
    }

}