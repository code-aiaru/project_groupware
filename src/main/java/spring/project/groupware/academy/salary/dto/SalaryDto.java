package spring.project.groupware.academy.salary.dto;

import lombok.*;
import spring.project.groupware.academy.employee.entity.EmployeeEntity;
import spring.project.groupware.academy.salary.entity.Salary;
import spring.project.groupware.academy.student.entity.StudentEntity;

import java.time.LocalDate;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SalaryDto {

    private Long id;

    // 지급일
    private LocalDate salaryDate;

    // 기본, 고정급
    private int baseSalary;

    // 추가 급여
    private int insentiveSalary;

    private EmployeeEntity employee;

    private StudentEntity student;

    public static SalaryDto toSalaryDto(Salary salary){
        SalaryDto salaryDto = SalaryDto.builder()
                .id(salary.getId())
                .salaryDate(salary.getSalaryDate())
                .baseSalary(salary.getBaseSalary())
//                .extraSalary(salary.getExtraSalary())
                .employee(salary.getEmployee())
//                .student(salary.getStudent())
                .build();
        return salaryDto;
    }

}
