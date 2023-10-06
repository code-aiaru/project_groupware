package spring.project.groupware.academy.board.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDto {
    private Long boardId;
    private String title;
    private String content;
//    private EmployeeEntity writer;
}
