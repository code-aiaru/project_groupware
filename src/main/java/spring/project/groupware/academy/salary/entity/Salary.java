package spring.project.groupware.academy.salary.entity;

import lombok.*;
import spring.project.groupware.academy.employee.entity.EmployeeEntity;
import spring.project.groupware.academy.student.entity.StudentEntity;

import javax.persistence.*;
import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "salary")
public class Salary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 지급일
    private LocalDate salaryDate;

    // 기본, 고정급
    private int baseSalary;

    // 추가, 급여
    //@ElementCollection
//    private int insentiveSalary;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_no")
    private EmployeeEntity employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private StudentEntity student;


}