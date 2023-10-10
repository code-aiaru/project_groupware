package spring.project.groupware.academy.board.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDto {
    private Long id;
    private String title;
    private String content;

    public BoardDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
//    private EmployeeEntity writer;
}
