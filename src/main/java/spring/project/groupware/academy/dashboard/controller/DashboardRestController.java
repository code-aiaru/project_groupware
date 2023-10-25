package spring.project.groupware.academy.dashboard.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import spring.project.groupware.academy.attendance.entity.AttendanceStatus;
import spring.project.groupware.academy.employee.config.MyUserDetails;
import spring.project.groupware.academy.employee.entity.EmployeeEntity;
import spring.project.groupware.academy.dashboard.service.DashboardService;
import spring.project.groupware.academy.post.entity.Notice;

import java.util.HashMap;
import java.util.Map;
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dashboard")
public class DashboardRestController {

    private final DashboardService dashboardService;

    @GetMapping("/approval")
    public Map<String, Object> getApprovalData(@AuthenticationPrincipal MyUserDetails myUserDetails) {
        log.info("return response : ApprovalData");

        EmployeeEntity employeeEntity = myUserDetails.getEmployeeEntity();
        Long pendingApprovalsCount = dashboardService.getPendingApprovalsCountForUser(employeeEntity);
        Long rejectedApprovalsCount = dashboardService.getRejectedApprovalsCountForUser(employeeEntity);
        Long approvedApprovalsCount = dashboardService.getApprovedApprovalsCountForUser(employeeEntity);

        Map<String, Object> response = new HashMap<>();
        response.put("pendingApprovalsCount", pendingApprovalsCount);
        response.put("rejectedApprovalsCount", rejectedApprovalsCount);
        response.put("approvedApprovalsCount", approvedApprovalsCount);

        return response;
    }

    @GetMapping("/attendance")
    public Map<String, Object> getAttendanceData(@RequestParam String className) {
        log.info("return response : AttendanceData");

        Map<AttendanceStatus, Long> attendanceCounts = dashboardService.getAttendanceCountsByClassForToday(className);

        Map<String, Object> response = new HashMap<>();
        for (Map.Entry<AttendanceStatus, Long> entry : attendanceCounts.entrySet()) {
            response.put(entry.getKey().toString(), entry.getValue());
        }

        return response;
    }

    @GetMapping("/notice")
    public Map<String, Object> getNoticeData() {
        log.info("return response : NoticeData");

        Page<Notice> lastFiveArticlesFromNotice = dashboardService.getLastFiveArticlesFromNotice();

        Map<String, Object> response = new HashMap<>();
        response.put("lastFiveArticlesFromNotice", lastFiveArticlesFromNotice);


        return response;
    }
}
