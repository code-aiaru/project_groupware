package spring.project.groupware.academy.employee.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.project.groupware.academy.attendance.entity.Attendance;
import spring.project.groupware.academy.employee.entity.EmployeeEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {

    Optional<EmployeeEntity> findByEmployeeId(String employeeId);

    // 아이디, 비밀번호 찾기
    EmployeeEntity findByEmployeeEmailAndEmployeePhone(String employeeEmail, String employeePhone);

    // 비밀번호
    Optional<EmployeeEntity> findByEmployeeEmail(String memberEmail);

    // 중복 검사
    boolean existsByEmployeeId(String employeeId);
    // 중복 검사
    boolean existsByEmployeeEmail(String employeeEmail);
    // 중복 검사
    boolean existsByEmployeePhone(String employeePhone);

    Page<EmployeeEntity> findByEmployeeIdContaining(Pageable pageable, String employeeId);
    Page<EmployeeEntity> findByEmployeeNameContaining(Pageable pageable, String employeeName);
    Page<EmployeeEntity> findByEmployeePhoneContaining(Pageable pageable, String employeePhone);
    Page<EmployeeEntity> findByEmployeeEmailContaining(Pageable pageable, String employeeEmail);
    Page<EmployeeEntity> findByEmployeeDepContaining(Pageable pageable, String employeeDep);
    Page<EmployeeEntity> findByEmployeePositionContaining(Pageable pageable, String employeePosition);
    Page<EmployeeEntity> findByEmployeeBirthContaining(Pageable pageable, String employeeBirth);
    Page<EmployeeEntity> findByEmployeeStreetAddressContaining(Pageable pageable, String employeeStreetAddress);
    Page<EmployeeEntity> findByEmployeeNoContaining(Pageable pageable, String employeeNo);


}
