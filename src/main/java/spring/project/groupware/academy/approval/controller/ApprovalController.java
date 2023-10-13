package spring.project.groupware.academy.approval.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spring.project.groupware.academy.approval.dto.ApprovalDto;
import spring.project.groupware.academy.approval.dto.ApprovalUserDto;
import spring.project.groupware.academy.approval.service.ApprovalService;
import spring.project.groupware.academy.approval.service.ApprovalUserService;
import spring.project.groupware.academy.employee.config.MyUserDetails;
import spring.project.groupware.academy.employee.dto.EmployeeDto;
import spring.project.groupware.academy.employee.service.EmployeeService;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/approval")
@RequiredArgsConstructor
public class ApprovalController {

    private final EmployeeService employeeService;
    private final ApprovalService approvalService;
    private final ApprovalUserService approvalUserService;


    @GetMapping("/write")
    public String getwrite(@PageableDefault(page=0, size=2, sort = "employeeNo", direction = Sort.Direction.DESC) Pageable pageable,
                           @RequestParam(value = "subject", required = false) String subject,
                           @RequestParam(value = "search", required = false) String search,
                           ApprovalDto approvalDto, Model model) {
        subject="";
        search="";
        Page<EmployeeDto> employeeDtoPage = employeeService.employeeList(pageable, subject, search);
        model.addAttribute("employeePage", employeeDtoPage);

        return "approval/write";
    }

    @PostMapping("/write")
    public String postwrite(@AuthenticationPrincipal MyUserDetails myUserDetails, ApprovalDto approvalDto) {
        Long[] dataArray = approvalDto.getDataArray();
        int newApprovalUserArrayLength = dataArray.length / 2;
        ApprovalUserDto[] newApprovalUserArray = new ApprovalUserDto[newApprovalUserArrayLength];

        for (int i = 0; i < newApprovalUserArrayLength; i++) {
            int firstIndex = i * 2;
            int secondIndex = firstIndex + 1;

            Long firstValue = dataArray[firstIndex];
            Long secondValue = dataArray[secondIndex];


            ApprovalUserDto newApprovalUserDto = new ApprovalUserDto();

            newApprovalUserDto.setId(firstValue);
            newApprovalUserDto.setAp(secondValue);

            newApprovalUserArray[i] = newApprovalUserDto;

        }

        List<ApprovalUserDto> approvalUserDtoList = new ArrayList<>();

        for (ApprovalUserDto approvalUserDto : newApprovalUserArray) {
            ApprovalUserDto modifiedApprovalUser = new ApprovalUserDto();
            modifiedApprovalUser.setId(approvalUserDto.getId());
            modifiedApprovalUser.setAp(approvalUserDto.getAp());
            approvalUserDtoList.add(modifiedApprovalUser);
        }
        String employeeId = myUserDetails.getUsername();
        Long approvalId = approvalService.approvalWrite(approvalDto, employeeId);
        approvalUserService.approvalUserCreate(approvalUserDtoList, approvalId);
        return "redirect:/approval/list";

    }

    @GetMapping("/list")
    public String getlist(@AuthenticationPrincipal MyUserDetails myUserDetails,
                          @PageableDefault(page = 0, size = 10, sort = "approval_id", direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        String employeeId = myUserDetails.getUsername();
        Page<ApprovalDto> approvalDtoPage = approvalService.approvalListPage(pageable, employeeId);
        int totalPage = approvalDtoPage.getTotalPages();
        int nowPage = approvalDtoPage.getNumber();
        int blockNum = 5;
        int pSize = approvalDtoPage.getSize();

        int startPage = (int) ((Math.ceil(nowPage / blockNum) * blockNum) + 1 <= totalPage ? (Math.ceil(nowPage / blockNum) * blockNum) + 1 : totalPage);
        int endPage = (startPage + blockNum - 1 < totalPage ? startPage + blockNum - 1 : totalPage);

        if (!approvalDtoPage.isEmpty()) {
            model.addAttribute("approvalList", approvalDtoPage);
            model.addAttribute("myUserDetails", myUserDetails);
            model.addAttribute("startPage", startPage);
            model.addAttribute("endPage", endPage);
            model.addAttribute("pSize", pSize);
//            model.addAttribute("subject", subject);
//            model.addAttribute("search", search);
            return "approval/list";
        }
        return "redirect:/approval/write";

    }
    @GetMapping("/detail/{id}")
    public String getdetail(@PathVariable("id") Long id,
                            @AuthenticationPrincipal MyUserDetails myUserDetails,
                            Model model) {
        ApprovalDto approvalDto = approvalService.approvalDetail(id);
        List<ApprovalUserDto> approvalUserDtoList = approvalUserService.approvalUserList(approvalDto.getId());


        model.addAttribute("approval", approvalDto);
        model.addAttribute("approvalUserList", approvalUserDtoList);
        model.addAttribute("myUserDetails", myUserDetails);
        return "approval/detail";
    }

    @GetMapping("/delete/{id}")
    public String getdelete(@PathVariable("id") Long id,
                            @AuthenticationPrincipal MyUserDetails myUserDetails,
                            Model model) {
        int rs = approvalService.approvalDelete(id);
        if (rs == 1) {
            System.out.println(("삭제 Success!"));
        } else {
            System.out.println(("삭제 Fail!"));
        }
        return "redirect:/approval/list";
    }

    @PostMapping("/ap")
    public String getap(ApprovalDto approvalDto){
        Long approvalId=approvalDto.getId();
        int rs = approvalService.approvalAp(approvalDto);
        if (rs == 1) {
            System.out.println(("수정 Success!"));
        } else {
            System.out.println(("수정 Fail!"));
        }
        return "redirect:/approval/detail/"+approvalId;
    }
}
