package spring.project.groupware.academy.dashboard.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.project.groupware.academy.approval.dto.ApprovalDto;
import spring.project.groupware.academy.approval.entity.ApprovalEntity;
import spring.project.groupware.academy.approval.repositroy.ApprovalRepository;
import spring.project.groupware.academy.attendance.entity.AttendanceStatus;
import spring.project.groupware.academy.attendance.repository.AttendanceRepository;
import spring.project.groupware.academy.employee.entity.EmployeeEntity;
import spring.project.groupware.academy.student.entity.StudentEntity;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final ApprovalRepository approvalRepository;
    private final AttendanceRepository attendanceRepository;

    // 대기 중인 결재 수 받아오는 메서드
    public Long getPendingApprovalsCountForUser(EmployeeEntity employeeEntity) {
        return approvalRepository.countByApprovalStatusAndEmployeeEntity("대기", employeeEntity);
    }

    // 반려된 결재 수 받아오는 메서드
    public Long getRejectedApprovalsCountForUser(EmployeeEntity employeeEntity) {
        return approvalRepository.countByApprovalStatusAndEmployeeEntity("거부", employeeEntity);
    }

    // 반려된 결재 수 받아오는 메서드
    public Long getApprovedApprovalsCountForUser(EmployeeEntity employeeEntity) {
        LocalDate currentDate = LocalDate.now(); // Java 8의 java.time.LocalDate 사용
        return approvalRepository.countByApprovedApprovalAndEmployeeEntity("승인", employeeEntity);
    }



    // 출결현황
    //     IN,OUT,LATE,SICK,VACATION,OUTING,ABSENT

    public Map<AttendanceStatus, Long> getAttendanceCountsByClass(String className) {
        Map<AttendanceStatus, Long> resultMap = new HashMap<>();

        for (AttendanceStatus status : AttendanceStatus.values()) {
            Long count = attendanceRepository.countAttendanceByStudentClass(status, className);
            resultMap.put(status, count);
        }

        return resultMap;
    }



}
