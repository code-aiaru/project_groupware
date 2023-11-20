package spring.project.groupware.academy.attendance.entity;

import lombok.*;
import spring.project.groupware.academy.attendance.dto.AttendanceDto;
import spring.project.groupware.academy.employee.entity.EmployeeEntity;
import spring.project.groupware.academy.student.entity.StudentEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Builder
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Table(name = "attendance")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // 출/퇴근 일 기록
    private LocalTime inAtt;
    private LocalTime outAtt;
    private LocalDate attDate;

    @Enumerated(EnumType.STRING)
    private AttendanceStatus attendanceStatus;

    // 휴/병가, 신청일 기록
    private LocalDate start;
    private LocalDate end;
    private LocalDate applyDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_no")
    private EmployeeEntity employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private StudentEntity student;


//    // 직원 쪽 엔티티에 추가
//    // 출석 연관 관계
//    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
//    private List<Attendance> attends = new ArrayList<>();

    public void changeInTime(LocalTime inAtt){
        this.inAtt = inAtt;
    }

    public void changeOutTime(LocalTime outAtt){
        this.outAtt = outAtt;
    }

    public void changeAttStatus(AttendanceStatus attendanceStatus){
        this.attendanceStatus = attendanceStatus;
    }


//    public void add(Attendance attendance){
//        attendance.setEmployee(this);
//        this.attends.add(attendance);
//    }

//    @Builder
//    public Attendance(Long id, LocalDate attDate, LocalDateTime inAtt){
//        this.id=id;
//        this.attDate=attDate;
//        this.inAtt=inAtt;
//    }

    public static Attendance toAttendance(AttendanceDto attendanceDto) {
        Attendance attendance = new Attendance();
        attendance.setId(attendanceDto.getId());
        attendance.setAttDate(attendanceDto.getAttDate());
        attendance.setAttendanceStatus(attendanceDto.getAttendanceStatus());
        attendance.setEmployee(attendanceDto.getEmployee());
//        attendance.setStudent(attendanceDto.getStudent());
        attendance.setStart(attendanceDto.getStart());
        attendance.setEnd(attendanceDto.getEnd());
        attendance.setApplyDate(attendanceDto.getApplyDate());
        attendance.setInAtt(attendanceDto.getInAtt());
        attendance.setOutAtt(attendanceDto.getOutAtt());
        return attendance;
    }

//    public static Attendance toAttendanceUpdate(AttendanceDto attendanceDto){
//        Attendance attendance = new Attendance();
//
//        attendance.setId(attendanceDto.getId());
//        attendance.setAttDate(attendanceDto.getAttDate());
//        attendance.setAttendanceStatus(attendanceDto.getAttendanceStatus());
//        attendance.setEmployee(attendanceDto.getEmployee());
//        attendance.setInAtt(attendanceDto.getInAtt());
//        attendance.setOutAtt(attendanceDto.getOutAtt());
//
//        return attendance;
//    }


}
