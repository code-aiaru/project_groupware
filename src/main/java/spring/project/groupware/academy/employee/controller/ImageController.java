package spring.project.groupware.academy.employee.controller;//package spring.project.groupware.academy.employee.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import spring.project.groupware.academy.employee.config.MyUserDetails;
import spring.project.groupware.academy.employee.dto.ImageUploadDto;
import spring.project.groupware.academy.employee.entity.EmployeeEntity;
import spring.project.groupware.academy.employee.service.EmployeeService;
import spring.project.groupware.academy.employee.service.ImageService;
import spring.project.groupware.academy.student.entity.StudentEntity;
import spring.project.groupware.academy.student.repository.StudentRepository;
import spring.project.groupware.academy.util.FileStorageService;

import java.io.IOException;

@RequiredArgsConstructor
@Controller
@RequestMapping("/image")
public class ImageController {

    private final ImageService imageService;
    private final StudentRepository studentRepository;


    // 이미지 등록
    @PostMapping("/upload")
    public String upload(@ModelAttribute ImageUploadDto imageUploadDto, @AuthenticationPrincipal MyUserDetails myUserDetails, Model model){

        EmployeeEntity employeeEntity = myUserDetails.getEmployeeEntity();
        imageService.upload(imageUploadDto, employeeEntity.getEmployeeId());

        // 이미지 URL을 모델에 추가
        model.addAttribute("employeeImageUrl", "/employeeImages/" + imageUploadDto.getFile().getOriginalFilename());

        return "redirect:/employee/detail/" + myUserDetails.getEmployeeEntity().getEmployeeNo();
    }

    // 학생 이미지 등록
    @PostMapping("/upload2")
    public String upload2(@ModelAttribute ImageUploadDto imageUploadDto, Long studentId, Model model){

        StudentEntity studentEntity = studentRepository.findByStudentId(studentId);
        imageService.upload2(imageUploadDto, studentEntity.getStudentId());

        // 이미지 URL을 모델에 추가
        model.addAttribute("studentImageUrl", "/studentImages/" + imageUploadDto.getFile().getOriginalFilename());

        return "redirect:/student/detail/" + studentEntity.getStudentId();
    }

    // 이미지 삭제
    @PostMapping("/delete")
    public String deleteImage(@AuthenticationPrincipal MyUserDetails myUserDetails) {

        String employeeId = myUserDetails.getUsername();
        imageService.deleteImage(employeeId);
        return "redirect:/employee/detail/" + myUserDetails.getEmployeeEntity().getEmployeeNo();
    }

    // 학생 이미지 삭제
    @PostMapping("/delete2")
    public String deleteImage2(Long studentId) {

        StudentEntity studentEntity = studentRepository.findByStudentId(studentId);
        imageService.deleteImage2(studentId);
        return "redirect:/student/detail/" + studentEntity.getStudentId();
    }

}
