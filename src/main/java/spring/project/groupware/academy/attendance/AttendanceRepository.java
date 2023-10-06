package spring.project.groupware.academy.attendance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance,Long> {

////    @Transactional
//    @Modifying
//    @Query(value = "select * from Attendance where attDate=:day ")
//    List<Attendance> findAllDate(@Param("day") LocalDate day);
//
//    @Modifying
//    @Query(value = "select count(*) from Attendance where attDate=:day")
//    int findByDate(@Param("day") LocalDate day);

//    select count(*) from Attendance where 출석=:1 and 날짜~ 비트윈 ~날짜



}
