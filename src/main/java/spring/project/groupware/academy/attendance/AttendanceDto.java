package spring.project.groupware.academy.attendance;

import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
