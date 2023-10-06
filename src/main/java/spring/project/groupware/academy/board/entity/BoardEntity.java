package spring.project.groupware.academy.board.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Board")
public class BoardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;

    public BoardEntity(String title, String content) {
    }

//    @ManyToOne
//    @JoinColumn(name = "employee_id")
//    private EmployeeEntity writer;




}
