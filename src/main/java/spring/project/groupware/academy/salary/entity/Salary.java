package spring.project.groupware.academy.salary.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@Getter
@Setter
public class Salary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate salaryday;

    private int salary;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee")
    private Employee employee;


}
