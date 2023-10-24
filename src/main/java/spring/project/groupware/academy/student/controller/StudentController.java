package spring.project.groupware.academy.student.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import spring.project.groupware.academy.employee.config.MyUserDetails;
import spring.project.groupware.academy.employee.dto.EmployeeDto;
import spring.project.groupware.academy.employee.service.EmployeeService;
import spring.project.groupware.academy.employee.service.ImageService;
import spring.project.groupware.academy.student.dto.StudentDto;
import spring.project.groupware.academy.student.service.StudentService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
//@RequestMapping("/student")
public class StudentController {

    // 학생 Controller

    private final StudentService studentService;
    private final EmployeeService employeeService;
    private final ImageService imageService;


    // Create
    @GetMapping("/student/join")
    public String getJoin(StudentDto studentDto, Model model) {

        List<Integer> birthYears = new ArrayList<>();
        for (int year = 2023; year >= 1900; year--) { // 2023부터 1900까지 역순으로 추가
            birthYears.add(year);
        }
        List<Integer> birthMonths = new ArrayList<>();
        for (int month = 1; month <= 12; month++) {
            birthMonths.add(month);
        }
        List<Integer> birthDays = new ArrayList<>();
        for (int day = 1; day <= 31; day++) {
            birthDays.add(day);
        }

        model.addAttribute("birthYears", birthYears);
        model.addAttribute("birthMonths", birthMonths);
        model.addAttribute("birthDays", birthDays);

        return "student/join";
    }

    @PostMapping("/post/student/join")
    public String postJoin(@ModelAttribute StudentDto studentDto) {

        String birthDate = String.format("%04d%02d%02d", studentDto.getBirthYear(), studentDto.getBirthMonth(), studentDto.getBirthDay());
        studentDto.setStudentBirth(birthDate);
        studentService.insertStudent(studentDto);

        return "redirect:/student/studentList?page=0&subject=&search=";
    }

    // 수강생관리 화면
    @GetMapping("/student/manage")
    public String getStudentManage(){
        return "student/manage";
    }

    // Read - 수강생 목록
    @GetMapping("/student/studentList")
    public String getStudentList(
            @PageableDefault(page=0, size=5, sort = "studentId", direction = Sort.Direction.DESC) Pageable pageable,
            Model model,
            @RequestParam(value = "subject", required = false) String subject,
            @RequestParam(value = "search", required = false) String search,
            @AuthenticationPrincipal MyUserDetails myUserDetails
    ) {

        if (myUserDetails != null) {
            EmployeeDto employee = employeeService.detailEmployee(myUserDetails.getEmployeeEntity().getEmployeeNo());
//            String employeeImageUrl = imageService.findImage(employee.getEmployeeId()).getImageUrl();

            model.addAttribute("employee", employee);
//            model.addAttribute("employeeImageUrl", employeeImageUrl);
            model.addAttribute("myUserDetails", myUserDetails);
        }

        Page<StudentDto> studentList = studentService.studentList(pageable, subject, search);

        Long totalCount = studentList.getTotalElements();
        int totalPage = studentList.getTotalPages();
        int pageSize = studentList.getSize();
        int nowPage = studentList.getNumber();
        int blockNum = 10;

        int startPage = (int) ((Math.floor(nowPage / blockNum) * blockNum) + 1 <= totalPage ?
                (Math.floor(nowPage / blockNum) * blockNum) + 1 : totalPage);
        int endPage = (startPage + blockNum - 1 < totalPage ? startPage + blockNum - 1 : totalPage);

        model.addAttribute("studentList", studentList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "student/studentList";
    }

    // Detail - 학생 상세 보기
    @GetMapping("/student/detail/{studentId}")
    public String getDetail(@PathVariable("studentId") Long studentId, Model model){

        StudentDto student = studentService.detailStudent(studentId);
        // 이미지 url을 db에서 가져오기
        String studentImageUrl = imageService.findImage2(student.getStudentId()).getImageUrl();

        model.addAttribute("student", student);
        model.addAttribute("studentImageUrl", studentImageUrl); // 이미지 url 모델에 추가

        return "student/detail";
    }

    // Update - 회원 수정 화면
    @GetMapping("/student/update/{studentId}")
    public String getUpdate(@PathVariable("studentId") Long studentId, StudentDto studentDto, Model model){

        // 연도, 월, 일 데이터를 모델에 추가하여 뷰로 전달
        List<Integer> birthYears = new ArrayList<>();
        for (int year = 2023; year >= 1900; year--) { // 2023부터 1900까지 역순으로 추가
            birthYears.add(year);
        }
        List<Integer> birthMonths = new ArrayList<>();
        for (int month = 1; month <= 12; month++) {
            birthMonths.add(month);
        }
        List<Integer> birthDays = new ArrayList<>();
        for (int day = 1; day <= 31; day++) {
            birthDays.add(day);
        }

        model.addAttribute("birthYears", birthYears);
        model.addAttribute("birthMonths", birthMonths);
        model.addAttribute("birthDays", birthDays);

        studentDto = studentService.updateViewStudent(studentId);
        model.addAttribute("studentDto", studentDto);

        return "student/update";
    }


    // Update - 실제 실행
    @PostMapping("/post/student/update")
    public String postUpdate(StudentDto studentDto) {

        // 생년월일 정보를 조합하여 하나의 문자열로 만듭니다.
        String birthDate = String.format("%04d%02d%02d", studentDto.getBirthYear(), studentDto.getBirthMonth(), studentDto.getBirthDay());
        
        studentDto.setStudentBirth(birthDate);

        int rs = studentService.updateStudent(studentDto);

        if (rs == 1) {
            System.out.println("학생정보 수정 성공");
            return "redirect:/student/detail/" + studentDto.getStudentId(); // 수정된 정보를 보여주는 상세 페이지로 이동

        } else {
            System.out.println("학생정보 수정 실패");
            return "student/update";
        }
    }


    // Delete - 학생 삭제(일반 사원도 가능)
    @GetMapping("/student/delete/{studentId}")
    public String getDelete(@PathVariable("studentId") Long studentId){

        int rs=studentService.deleteStudent(studentId);

        if (rs==1) {
            System.out.println("학생 삭제 성공");
            return "redirect:/student/studentList?page=0&subject=&search=";

        }else{
            System.out.println("학생 삭제 실패");
            return "redirect:/";
        }
    }


    // 프로필 이미지 변경 페이지
    @GetMapping("/student/updateImage/{studentId}")
    public String getUpdateImage(@PathVariable("studentId") Long studentId, Model model){

        StudentDto student = studentService.detailStudent(studentId);

        // 이미지 url을 db에서 가져오기
        String studentImageUrl = imageService.findImage2(student.getStudentId()).getImageUrl();

        model.addAttribute("student", student);
        model.addAttribute("studentImageUrl", studentImageUrl); // 이미지 url 모델에 추가

        return "student/updateImage";
    }





}
