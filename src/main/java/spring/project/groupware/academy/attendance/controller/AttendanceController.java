package spring.project.groupware.academy.attendance.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spring.project.groupware.academy.attendance.dto.AttendanceDto;
import spring.project.groupware.academy.attendance.repository.AttendanceRepository;
import spring.project.groupware.academy.attendance.service.AttendanceService;
import spring.project.groupware.academy.employee.repository.EmployeeRepository;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;
    private final AttendanceRepository attendanceRepository;

    private final EmployeeRepository employeeRepository;

// 인트로 존재 유무 받아서 조건문 추가

    @GetMapping("/in/{id}")
    public String inAttend(@PathVariable("id") Long id) {
        attendanceService.inAttend(id);

        return "redirect:/attendance/list2";
    }

    @GetMapping("/out/{id}")
    public String outAttend(@PathVariable("id") Long id) {
        attendanceService.outAttend(id);
        return "redirect:/attendance/list2";
    }

    @GetMapping("/list")
    public String AttendList(Model model) {
        List<AttendanceDto> attendanceList = attendanceService.attendanceList();

        model.addAttribute("attendanceList", attendanceList);

        return "/attendanceList";
    }

    @GetMapping("/detail/{id}")
    public String detailAttend(@PathVariable("id") Long id,Model model) {
        List<AttendanceDto> attendanceList = attendanceService.detailAttend(id);

        model.addAttribute("attendanceList",attendanceList);
        return "/attendanceList";
    }

    //    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    @GetMapping("/list2")
    public String attPageList1(@PageableDefault(page = 0, size = 30, sort = "attDate",
            direction = Sort.Direction.ASC) Pageable pageable,
                               @RequestParam(value = "subject", required = false) String subject,
                               @RequestParam(value = "set", required = false) String set,
                               @RequestParam(value = "first", required = false) String first,
                               @RequestParam(value = "last", required = false) String last,
                               Model model){


        Page<AttendanceDto> attPageList = attendanceService.attendancePagingList1(pageable, subject, set,first, last);

        Long totalCount = attPageList.getTotalElements();
        int pagesize = attPageList.getSize();
        int nowPage = attPageList.getNumber();
        int totalPage = attPageList.getTotalPages();
        int blockNum = 5;

        int startPage =
                (int) ((Math.floor(nowPage / blockNum) * blockNum) + 1 <= totalPage ? (Math.floor(nowPage / blockNum) * blockNum) + 1 : totalPage);
        int endPage =
                (startPage + blockNum - 1 < totalPage ? startPage + blockNum - 1 : totalPage);
        for (int i = startPage; i <= endPage; i++) {
            System.out.println(i + " , ");
        }

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("attPageNo", attPageList);


        model.addAttribute("attPageList", attPageList);

        return "/attendanceList2";
    }

////    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
//    @GetMapping("/list2")
//    public String attPageList2(@PageableDefault(page = 0, size = 30, sort = "attDate",
//            direction = Sort.Direction.ASC) Pageable pageable,
//            @PathVariable("id") Long id,
//            @RequestParam(value = "subject", required = false) String subject,
//            @RequestParam(value = "set", required = false) String set,
//            @RequestParam(value = "first", required = false) String first,
//            @RequestParam(value = "last", required = false) String last,
//            Model model){
//
//
//        Page<AttendanceDto> attPageList = attendanceService.attendancePagingList2(pageable, id, subject, set,first, last);
//
//        Long totalCount = attPageList.getTotalElements();
//        int pagesize = attPageList.getSize();
//        int nowPage = attPageList.getNumber();
//        int totalPage = attPageList.getTotalPages();
//        int blockNum = 5;
//
//        int startPage =
//                (int) ((Math.floor(nowPage / blockNum) * blockNum) + 1 <= totalPage ? (Math.floor(nowPage / blockNum) * blockNum) + 1 : totalPage);
//        int endPage =
//                (startPage + blockNum - 1 < totalPage ? startPage + blockNum - 1 : totalPage);
//        for (int i = startPage; i <= endPage; i++) {
//            System.out.println(i + " , ");
//        }
//
//        model.addAttribute("startPage", startPage);
//        model.addAttribute("endPage", endPage);
//        model.addAttribute("attPageNo", attPageList);
//
//
//        model.addAttribute("attPageList", attPageList);
//
//        return "/attendanceList2";
//    }


//    @GetMapping("/sick/{id}")
//    public String sickAttendG() {
//        return "";
//    }
//
//    @PostMapping("/sick")
//    public String sickAttendP() {
//        return "";
//    }

//    @PostMapping("/vacation")
//    public void vacationAttend() {
//        attendanceService.vacationAttend();
//    }


}
