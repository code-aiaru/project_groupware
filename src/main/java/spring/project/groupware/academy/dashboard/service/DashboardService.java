package spring.project.groupware.academy.dashboard.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import spring.project.groupware.academy.approval.dto.ApprovalDto;
import spring.project.groupware.academy.approval.entity.ApprovalEntity;
import spring.project.groupware.academy.approval.repositroy.ApprovalRepository;
import spring.project.groupware.academy.attendance.entity.AttendanceStatus;
import spring.project.groupware.academy.attendance.repository.AttendanceRepository;
import spring.project.groupware.academy.employee.entity.EmployeeEntity;
import spring.project.groupware.academy.post.entity.Notice;
import spring.project.groupware.academy.post.repository.NoticeRepository;

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
    private final NoticeRepository noticeRepository;

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
        return approvalRepository.countByApprovedApprovalAndEmployeeEntity("승인", employeeEntity);
    }



    // 오늘의 출결현황
    public Map<AttendanceStatus, Long> getAttendanceCountsByClassForToday(String className) {
        Map<AttendanceStatus, Long> resultMap = new HashMap<>();

        for (AttendanceStatus status : AttendanceStatus.values()) {
            Long count = attendanceRepository.countAttendanceByStudentClass(status, className);
            resultMap.put(status, count);
        }

        return resultMap;
    }

    // 최근 공지 5개 받아오는 메서드
    public Page<Notice> getLastFiveArticlesFromNotice() {
        return noticeRepository.findAllByOrderByIdDesc(PageRequest.of(0, 5));
    }
}

