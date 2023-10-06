//package spring.project.groupware.academy.employee.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import spring.project.groupware.academy.employee.config.MyUserDetails;
//import spring.project.groupware.academy.employee.dto.ImageUploadDto;
//import spring.project.groupware.academy.employee.entity.EmployeeEntity;
//import spring.project.groupware.academy.employee.service.ImageServiceImpl;
//
//@RequiredArgsConstructor
//@Controller
//@RequestMapping("/image")
//public class ImageController {
//
//    private final ImageServiceImpl imageService;
//
//    // 이미지 등록
//    @PostMapping("/upload")
//    public String upload(@ModelAttribute ImageUploadDto imageUploadDto, @AuthenticationPrincipal MyUserDetails myUserDetails, Model model){
//
//        EmployeeEntity employee = myUserDetails.getEmployeeEntity();
//        imageService.upload(imageUploadDto, employee.getEmployeeId());
//
//        // 이미지 URL을 모델에 추가
//        model.addAttribute("memberImageUrl", "/profileImages/" + imageUploadDto.getFile().getOriginalFilename());
//
//        return "redirect:/member/detail/" + myUserDetails.getEmployeeEntity().getEmployeeNo();
//    }
//
//    // 이미지 삭제
//    @PostMapping("/delete")
//    public String deleteImage(@AuthenticationPrincipal MyUserDetails myUserDetails) {
//
//        String employeeId = myUserDetails.getUsername();
//        imageService.deleteImage(employeeId);
//        return "redirect:/member/detail/" + myUserDetails.getEmployeeEntity().getEmployeeNo();
//    }
//
//}
