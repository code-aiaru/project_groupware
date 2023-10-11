package spring.project.groupware.academy.attendance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import spring.project.groupware.academy.attendance.entity.Attendance;
import spring.project.groupware.academy.employee.entity.EmployeeEntity;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance,Long> {

//    List<Attendance> findByEmployeeAndAttDate(EmployeeEntity employee_no , LocalDate attDate);

//    Attendance findByEmployeeAndAttDate(EmployeeEntity emp, LocalDate now);
//
//    Long findByEmployeeAndAttDate(Long employee_no, LocalDate attDate);
//
//    List<Attendance> findByEmployeeId(Long employee_no);

    // 정상 작동함
//     List<Attendance> findByAttDate(LocalDate now);

    //   DB 명령문
////    select * from attendance where employee_no='1';
//    @Modifying
//    @Query(value = "select * from attendance where employee_no=:employee_no")
//    List<Attendance> employeeAtt(@Param("employee_no") Long employee_no);

    //mysql에서는 조회 확인됨
    //위는 잘못 표기 된게 무엇인지

    //문제 Attendance.employee 연관 관계 조회 employee? employee_no? employee_id?
    //Jpa findBy~~And~~
    //작성시 findBy필드명And필드명  이라면 entity 필드명이기는한데 (여기서도 바뀐 Colum이름인지 초기 필드명인지?) DB에 필드명?
    //받는 findByEmployee(Long employee) 매개변수 이름도 상관이 있는지?  //employee_no 실험 해보니까 상관 있는듯 함>>아닌듯함...






//    Attendance findByAttDate(LocalDate attDate);

//    List<Attendance> findByAttdate(Long employeeNo, LocalDate attDate);

//    Attendance findByDate(LocalDate attDate);

////    @Transactional
//    @Modifying
//    @Query(value = "select * from Attendance where attDate=:day ")
//    List<Attendance> findAllDate(@Param("day") LocalDate day);

//    @Modifying
//    @Query(value = "select count(*) from Attendance where attDate=:day")
//    int findByDate(@Param("day") LocalDate day);

//    @Modifying
//    @Query(value = "select * from Attendance where attDate=:day and employee_no=:id ")
//    @Query(value = "select a from Attendance a where a.employee.employee_no = :employee_no and a.attDate = :attDate")

//    @Modifying
//    @Query(value = "select * from attendance where employee_no=:employee_no and att_date=:att_date ")
//    List<Attendance> findByAttdate(@Param("employee_no") Long id,@Param("att_date") LocalDate day);

//    List<Attendance> findByEmployeeAndAttDate(Long employee_no, LocalDate attDate);

//    select count(*) from Attendance where 출석=:1 and 날짜~ 비트윈 ~날짜



}
