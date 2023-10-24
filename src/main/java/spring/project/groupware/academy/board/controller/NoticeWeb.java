//package spring.project.groupware.academy.board.controller;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import spring.project.groupware.academy.board.dto.NoticeDto;
//
//import java.util.List;
//
//@Controller
//@RequestMapping("/notice")
//public class NoticeWeb {
//
//    private final NoticeService noticeService;
//
//    public NoticeWeb(NoticeService noticeService) {
//        this.noticeService = noticeService;
//    }
//
//    @GetMapping("/create")
//    public String showCreateNoticeForm() {
//        return "notice/create";
//    }
//
//    @GetMapping("/list")
//    public String showNoticeList(Model model) {
//        List<NoticeDto> notices = noticeService.getAllNotices();
//        model.addAttribute("noticeList", notices);
//        return "notice/list";
//    }
//
//
//    @GetMapping("/detail/{id}")
//    public String showNoticeDetail(@PathVariable Long id, Model model) {
//        NoticeDto notice = noticeService.getNoticeById(id);
//        if (notice != null) {
//            model.addAttribute("notice", notice);
//            model.addAttribute("id", id);
//            return "notice/detail"; // "Notice-detail"은 게시물 상세 정보를 표시하는 템플릿 파일 이름입니다.
//        } else {
//            // 게시물이 없을 경우에 대한 처리
//            return "redirect:/notice/list"; // 목록 페이지로 리다이렉트합니다.
//        }
//    }
//
//
//    @GetMapping("/edit/{id}")
//    public String edit(@PathVariable Long id, Model model) {
//        NoticeDto noticeDto = noticeService.getNoticeById(id);
//        if (noticeDto != null) {
//        model.addAttribute("notice", noticeDto);
//        return "notice/edit"; // 수정 페이지의 템플릿 이름
//        }
//        return "redirect:/notice/list";
//    }
//
//}
