package spring.project.groupware.academy.attendance.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import spring.project.groupware.academy.attendance.entity.Attendance;
import spring.project.groupware.academy.attendance.entity.AttendanceStatus;
import spring.project.groupware.academy.employee.entity.EmployeeEntity;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance,Long>{


//    // 정상 작동함
//    List<Attendance> findByAttDate(LocalDate now);

    //   DB 명령문
////    select * from attendance where employee_no='1';
//    @Modifying
//    @Query(value = "select * from attendance where employee_no=:employee_no")
//    List<Attendance> employeeAtt(@Param("employee_no") Long employee_no);
    //mysql에서는 조회 확인됨
    //위는 잘못 표기 무엇?

    //문제 Attendance.employee 연관 관계 조회 employee? employee_no? employee_id?   // 해결댐
    //Jpa findBy~~And~~ // 해결

    //작성시 findBy필드명And필드명  이라면 entity 필드명이기는한데 (여기서도 바뀐 Colum이름인지 초기 필드명인지?) DB에 필드명?
    //받는 findByEmployee(Long employee) 매개변수 이름도 상관이 있는지?  //employee_no 실험 해보니까 상관 있는듯 함>>아닌듯함...

    // jpql
//    @Query(value = "SELECT a FROM Attendance a WHERE a.attDate =:attDate AND a.employee =:employee")
//    List<Attendance> customFindByAttDateAndEmployee(@Param("attDate")LocalDate attDate, @Param("employee") EmployeeEntity employee);

//    List<Attendance> findByAttDateAndEmployee(@Param("attDate")LocalDate attDate, @Param("employee") EmployeeEntity employee);

    Attendance findByAttDateAndEmployee(@Param("attDate")LocalDate attDate, @Param("employee") EmployeeEntity employee);


    // 해당 사원 출결 조회 >> 상세 누르면 나옴 // 일단 detail
    List<Attendance> findByEmployee(@Param("employee") EmployeeEntity employee);

    LocalDate findByAttendanceStatusAndEmployeeAndAttDate(@Param("attendanceStatus")AttendanceStatus attendanceStatus, @Param("employee") EmployeeEntity employee, @Param("attDate")LocalDate attDate);

    List<Attendance> findByAttDateAndAttendanceStatus(@Param("attDate")LocalDate attDate, @Param("attendanceStatus")AttendanceStatus attendanceStatus);

//    @Modifying
//    @Query(value = "select * from attendance where attendance_status=:attendanceStatus")
//    Page<Attendance> customAttendanceStatus(Pageable pageable, @Param("attendanceStatus")AttendanceStatus attendanceStatus);


    List<Attendance> findByAttendanceStatus(@Param("attendanceStatus") AttendanceStatus attendanceStatus);

    Page<Attendance> findByAttendanceStatus(Pageable pageable, @Param("attendanceStatus") AttendanceStatus attendanceStatus);


    Page<Attendance> findAllByAttDateBetween(Pageable pageable, LocalDate date1, LocalDate date2);
    Page<Attendance> findByAttDateBetween(Pageable pageable, LocalDate date1, LocalDate date2);

//    mysql
//    select * from attendance where attendance_status='ABSENT' and att_date between '2023-9-20' and '2023-10-10';

    List<Attendance> findByAttendanceStatusAndAttDateBetween(AttendanceStatus attendanceStatus, LocalDate date1, LocalDate date2);

    Page<Attendance> findByAttendanceStatusAndAttDateBetween(Pageable pageable, AttendanceStatus attendanceStatus, LocalDate date1, LocalDate date2);

    Page<Attendance> findAllByAttendanceStatus(Pageable pageable, AttendanceStatus attendanceStatus);

    // 해당 사원 정해진 기간동안 출결 조회     >> 상세 누르면 나오는 부분 // 일단 detail
    List<Attendance> findByEmployeeAndAttDateBetween(EmployeeEntity employee, LocalDate date1, LocalDate date2);
    Page<Attendance> findByEmployeeAndAttDateBetween(Pageable pageable, EmployeeEntity employee, LocalDate date1, LocalDate date2);

//    List<Attendance> findByAttendanceStatusContains(@Param("attendanceStatus")AttendanceStatus attendanceStatus);

//    List<Attendance> findByAttDateAndAttendanceStatusOrAttendanceStatus(@Param("attDate")LocalDate attDate, @Param("attendanceStatus")AttendanceStatus attendanceStatus1, @Param("attendanceStatus")AttendanceStatus attendanceStatus2);

//    select * from attendance where (attendance_status='LATE' or attendance_status='ABSENT') and att_date between '2023-9-20' and '2023-10-10';

//    mysql
//    select * from attendance where (attendance_status='LATE') and att_date between '2023-9-20' and '2023-10-10';
    //JPA ??

//    //jpql    // 실행 성공1
//    @Modifying
//    @Query("SELECT a FROM Attendance a WHERE (a.attendanceStatus = 'LATE' OR a.attendanceStatus = 'ABSENT') AND a.attDate BETWEEN :startDate AND :endDate")
//    List<Attendance> customAttDate(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    //jpql      // 실행 성공2
    @Modifying
    @Query("SELECT a FROM Attendance a WHERE (a.attendanceStatus =:attStatus1 OR a.attendanceStatus =:attStatus2 ) AND a.attDate BETWEEN :startDate AND :endDate")
    List<Attendance> customAttDate(@Param("attStatus1") AttendanceStatus attStatus1, @Param("attStatus2") AttendanceStatus attStatus2, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    //추가 예시 msql
    // 해당 사원 , 설정한 출근상태 기준으로, 정해진 기간1 ~ 기간2 까지
    //select * from attendance where employee_no='3' and attendance_status = 'OUT' and att_date between '2023-10-01' and '2023-10-10';
    List<Attendance> findByEmployeeAndAttendanceStatusAndAttDateBetween(EmployeeEntity emp, AttendanceStatus attendanceStatus, LocalDate of, LocalDate of1);
    // paging
    Page<Attendance> findByEmployeeAndAttendanceStatusAndAttDateBetween(Pageable pageable, EmployeeEntity emp, AttendanceStatus attendanceStatus, LocalDate of, LocalDate of1);

    Page<Attendance> findByEmployeeAndAttendanceStatus(Pageable pageable, EmployeeEntity emp, AttendanceStatus attendanceStatus);

    Page<Attendance> findAllByEmployee(Pageable pageable, EmployeeEntity employee);

    //nativeQuery
    //nativeQuery = true
//    @Modifying
//    @Query(value = "SELECT * FROM attendance WHERE (attendance_status = :attStatus1 OR attendance_status = :attStatus2) AND att_date BETWEEN :attDate1 AND :attDate2")
//    List<Attendance> customAttDate(@Param("attStatus1") String attStatus1, @Param("attStatus2") String attStatus2, @Param("attDate1") LocalDate date1, @Param("attDate2") LocalDate date2);


//    쿼리메서드,네이티브,jpql, 쿼리dsl
////    @Transactional


//    select count(*) from Attendance where 출석=:1 and 날짜~ 비트윈 ~날짜

//    countBy, existsBy
//    Long countByAttDate(String AttDate);
//    boolean existsByAttDate(String str);

//    SELECT *
//    FROM dept a
//    WHERE a.deptno IN (20, 30, 40)
//    AND EXISTS (SELECT 1
//            FROM emp b
//            WHERE b.sal between 500 and 1300
//            AND b.deptno = a.deptno)


}
