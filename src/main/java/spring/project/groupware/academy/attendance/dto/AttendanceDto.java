package spring.project.groupware.academy.attendance.dto;

import lombok.*;
import spring.project.groupware.academy.attendance.entity.AttendanceStatus;
import spring.project.groupware.academy.attendance.entity.Attendance;
import spring.project.groupware.academy.employee.entity.EmployeeEntity;
import spring.project.groupware.academy.student.entity.StudentEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AttendanceDto {

    private Long id;

    private java.time.LocalDateTime inAtt;
    private LocalDateTime outAtt;
    private LocalDate attDate;

    private AttendanceStatus attendanceStatus;

    private EmployeeEntity employee;
    private StudentEntity student;

//    public static class AttendanceStatusPage{
////        private AttendanceStatus attendanceStatus;
//        private List<AttendanceDto> attendanceDtoList;
//    }

    public static AttendanceDto toAttendanceDto(Attendance attendance){
        AttendanceDto attendanceDto = AttendanceDto.builder()
                .id(attendance.getId())
                .attDate(attendance.getAttDate())
                .inAtt(attendance.getInAtt())
                .outAtt(attendance.getOutAtt())
                .attendanceStatus(attendance.getAttendanceStatus())
                .build();
        return attendanceDto;
    }


}
