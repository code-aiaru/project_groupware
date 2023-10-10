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
import spring.project.groupware.academy.util.FileStorageService;

import java.io.IOException;

@RequiredArgsConstructor
@Controller
@RequestMapping("/image")
public class ImageController {

    private final FileStorageService fileStorageService;
    private final ImageService imageService;
    private final EmployeeService employeeService;

    // 이미지 등록
    @PostMapping("/upload")
    public String upload(@ModelAttribute ImageUploadDto imageUploadDto, @AuthenticationPrincipal MyUserDetails myUserDetails, Model model) throws IOException {

        EmployeeEntity employee = myUserDetails.getEmployeeEntity();
        imageService.saveEmployeeImage(employee, imageUploadDto.getFile());

        // 이미지 URL을 모델에 추가
        model.addAttribute("employeeImageUrl", "/employeeImages/" + imageUploadDto.getFile().getOriginalFilename());

        return "redirect:/employee/detail/" + myUserDetails.getEmployeeEntity().getEmployeeNo();
    }

    // 이미지 삭제
//    @PostMapping("/delete")
//    public String deleteImage(@AuthenticationPrincipal MyUserDetails myUserDetails) {
//
//        String employeeId = myUserDetails.getUsername();
//        fileStorageService.deleteImage(employeeId);
//        return "redirect:/employee/detail/" + myUserDetails.getEmployeeEntity().getEmployeeNo();
//    }

}
