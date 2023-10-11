package spring.project.groupware.academy.board.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Board")
public class BoardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;

    public BoardEntity(String title, String content) {
        this.title = title;
        this.content = content;
    }



//    @ManyToOne
//    @JoinColumn(name = "employee_id")
//    private EmployeeEntity writer;




}
