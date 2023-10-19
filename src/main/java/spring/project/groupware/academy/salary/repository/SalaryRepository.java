package spring.project.groupware.academy.salary.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.project.groupware.academy.employee.entity.EmployeeEntity;
import spring.project.groupware.academy.salary.entity.Salary;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, Long> {

    List<Salary> findByEmployeeAndSalaryDateBetween(EmployeeEntity employeeEntity, LocalDate start, LocalDate end);

    Page<Salary> findByEmployee(Pageable pageable, EmployeeEntity employee);

    Page<Salary> findByEmployeeAndSalaryDateBetween(Pageable pageable, EmployeeEntity employee, LocalDate start, LocalDate end);

}
