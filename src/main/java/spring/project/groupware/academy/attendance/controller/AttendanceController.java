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
import spring.project.groupware.academy.student.repository.StudentRepository;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;
    private final AttendanceRepository attendanceRepository;

    private final EmployeeRepository employeeRepository;
    private final StudentRepository studentRepository;


    @GetMapping("/join")
    public String join() {
        return "attendance/attendanceOutIn";
    }

    @GetMapping("/in/{id}")
    public String inAttend(@PathVariable("id") Long id) {
        attendanceService.inAttend1(id);

        return "redirect:/attendance/list2";
    }

//    @PathVariable("id") Long id, HttpServletRequest request) {
//        String referer = request.getHeader("Referer");
//        attendanceService.outAttend1(id);
//
//        return "redirect:"+referer;
//    }

    @GetMapping("/out/{id}")
    public String outAttend(@PathVariable("id") Long id) {
        attendanceService.outAttend1(id);

        return "redirect:/attendance/list2";
    }

    @GetMapping("/list")
    public String AttendList(Model model) {
        List<AttendanceDto> attendanceList = attendanceService.attendanceList();

        model.addAttribute("attendanceList", attendanceList);

        return "attendance/attendanceList2";
    }

    @GetMapping("/detail/{id}")
    public String detailAttend(@PathVariable("id") Long id,Model model) {
        List<AttendanceDto> attendanceList = attendanceService.detailAttend(id);

        model.addAttribute("attendanceList",attendanceList);
        return "attendance/attendanceList";
    }

    @GetMapping("/list2")
    public String attPageList1(@PageableDefault(page = 0, size = 30, sort = "attDate",
            direction = Sort.Direction.ASC) Pageable pageable,

//                               @RequestParam(value = "job", required = false) String job,

                               @RequestParam(value = "subject", required = false) String subject,
                               @RequestParam(value = "set", required = false) String set,
                               @RequestParam(value = "first", required = false) String first,
                               @RequestParam(value = "last", required = false) String last,
                               Model model){

        Page<AttendanceDto> attPageList = attendanceService.attendancePagingList1(pageable, subject, set, first, last);

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

        return "attendance/attendanceList2";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute AttendanceDto attendanceDto) {

        int rs = attendanceService.attendanceUpdate(attendanceDto);

        if (rs == 1) {
            System.out.println("수정 성공!!");
        } else {
            System.out.println("수정 실패!!");
        }
        return "";
    }

    @GetMapping("/update/{id}")
    public String updateok(@PathVariable("id") Long id, Model model) {

        AttendanceDto attendanceDto = attendanceService.attendanceUpdateOk(id);

        if (attendanceDto != null) {
            model.addAttribute("attendanceDto", attendanceDto);
            return "/attendance/update";
        }
        return "";
    }


//    @GetMapping("/list2/{id}")
//    public String attPageList2(@PageableDefault(page = 0, size = 30, sort = "attDate",
//            direction = Sort.Direction.ASC) Pageable pageable,
//            @AuthenticationPrincipal MyUserDetails myUserDetails,
//            @PathVariable("id") Long id,
//            @RequestParam(value = "subject", required = false) String subject,
//            @RequestParam(value = "set", required = false) String set,
//            @RequestParam(value = "first", required = false) String first,
//            @RequestParam(value = "last", required = false) String last,
//            Model model){
//
//        if (id==null) id = myUserDetails.getEmployeeEntity().getEmployeeNo();
//
//        Page<AttendanceDto> attPageList = attendanceService.attendancePagingList2(pageable, id, subject, set,first, last);
//
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
//        model.addAttribute("attPageList", attPageList);
//
//        return "/attendanceList2";
//    }


}