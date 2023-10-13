package spring.project.groupware.academy.board.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NoticeDto{
    private Long id;
    private String title;
    private String writer;
    private String content;



}
