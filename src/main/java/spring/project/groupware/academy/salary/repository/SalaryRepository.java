package spring.project.groupware.academy.salary.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import spring.project.groupware.academy.employee.entity.EmployeeEntity;
import spring.project.groupware.academy.salary.entity.Salary;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, Long> {

//    Salary findByEmployeeAndSalaryDateBetween(EmployeeEntity employeeEntity, LocalDate start, LocalDate end);

    List<Salary> findByEmployeeAndSalaryDateBetween(EmployeeEntity employeeEntity, LocalDate start, LocalDate end);

    Page<Salary> findByEmployee(Pageable pageable, EmployeeEntity employee);

    Page<Salary> findByEmployeeAndSalaryDateBetween(Pageable pageable, EmployeeEntity employee, LocalDate start, LocalDate end);

//    @Query("SELECT MIN(s.salaryDate) FROM Salary s AND s.employee_no:=employeeNo")
//    LocalDate findOldestSalaryDate(@Param("employeeNo") Long employeeNo);

    // 이 문구로 해야하는듯 함
//    select * from salary where min(salary_date) and employee_no='3';

//    @Query(value = "SELECT * FROM salary WHERE MIN(salary_date) AND employee_no =:employeeNo", nativeQuery = true)
//    Salary findOldestSalaryDateByEmployee(@Param("employeeNo") Long employeeNo);

    //nativeQuery
    @Query(value = "SELECT MIN(salary_date) FROM salary WHERE employee_no=:employeeNo", nativeQuery = true)
    LocalDate findOldestSalaryDateByEmployeeNo(@Param("employeeNo") Long employeeNo);

    Page<Salary> findBySalaryDateBetween(Pageable pageable, LocalDate start, LocalDate end);
    int countByEmployeeAndSalaryDateBetween(EmployeeEntity emp, LocalDate of, LocalDate of1);


//     LocalDate는 ASC,Desc가 불가 >> Desc
//    LocalDate findTopSalaryDateOrderBySalaryDateAsc();
//    LocalDate findTopEmployeeOrderBySalaryDateAsc(EmployeeEntity employee);

    //jpql
//    @Query("SELECT a FROM Salary a WHERE a.employee =:employee ORDER BY a.salary_date")
//    LocalDate customSalaryDate(@Param("employee") EmployeeEntity employee);

//    Page<Salary> findSalariesWithFilters(Pageable pageable, Long id, String subject, String set, String first, String last);

}
