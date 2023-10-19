package spring.project.groupware.academy.salary.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.project.groupware.academy.employee.config.MyUserDetails;
import spring.project.groupware.academy.salary.dto.SalaryDto;
import spring.project.groupware.academy.salary.repository.SalaryRepository;
import spring.project.groupware.academy.salary.service.SalaryService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/salary")
public class SalaryController {

    private final SalaryService salaryService;
    private final SalaryRepository salaryRepository;

    // 일반 급여 처리
    // 추가 급여 처리

    // 관리자가 사원들 급여 목록 확인 //나중에 paging 추가
    @GetMapping("/list")
    public String SalaryList(Model model) {
        List<SalaryDto> salaryDtoList = salaryService.salaryList();

        model.addAttribute("salaryDtoList", salaryDtoList);

        return "salary/salaryList";
    }

    //년,월, 급여차수or 상반기(1~6)하반기(7~12)
    //구분/성명/부서(수업)/지급총액/공제총액/실지급액
    //해당 사원만 보이게 //나중에 paging 추가 고민
    @GetMapping("/detail/{id}")
    public String detailSalary(@PathVariable("id") Long id,Model model) {
        List<SalaryDto> salaryDtoList = salaryService.detailSalary(id);

        model.addAttribute("salaryDtoList",salaryDtoList);
        return "salary/salaryDetail";
    }

    // 일반 list에서 페이지 추가
    @GetMapping("/page")
//    @GetMapping("/page/{id}")
    public String SalaryPageList(@PageableDefault(page = 0, size = 30, sort = "salaryDate",
            direction = Sort.Direction.ASC) Pageable pageable,
                                 @AuthenticationPrincipal MyUserDetails myUserDetails,
//                               @RequestParam(value = "id", required = false) Long id,
                               @RequestParam(value = "subject", required = false) String subject,
                               @RequestParam(value = "set", required = false) String set,
                               @RequestParam(value = "first", required = false) String first,
                               @RequestParam(value = "last", required = false) String last,
                               Model model){

        Long id = myUserDetails.getEmployeeEntity().getEmployeeNo();

        Page<SalaryDto> salaryDtoPage = salaryService.salaryPagingList(pageable, id, subject, set, first, last);

//        Long totalCount = salaryDtoPage.getTotalElements();
//        int pagesize = salaryDtoPage.getSize();
        int nowPage = salaryDtoPage.getNumber();
        int totalPage = salaryDtoPage.getTotalPages();
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
        model.addAttribute("salaryDtoPageNo", salaryDtoPage);


        model.addAttribute("salaryDtoPage", salaryDtoPage);

        return "salary/salaryPageList";
    }

}