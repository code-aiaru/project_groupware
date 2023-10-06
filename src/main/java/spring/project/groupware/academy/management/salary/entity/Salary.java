package spring.project.groupware.academy.management.salary.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
public class Salary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate salaryday;

    private int salary;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "employee")
//    private Employee employee;


}
