package spring.project.groupware.academy.post.Controller;



import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import spring.project.groupware.academy.post.dto.NoticeRequestDTO;
import spring.project.groupware.academy.post.dto.NoticeResponseDTO;
import spring.project.groupware.academy.post.dto.PostRequestDTO;
import spring.project.groupware.academy.post.dto.PostResponseDTO;
import spring.project.groupware.academy.post.service.NoticeService;
import spring.project.groupware.academy.post.service.PostService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;
    private final NoticeService noticeService;

    // 게시글 저장
    @PostMapping
    public Long savePost(@RequestBody final PostRequestDTO params) {
        return postService.savePost(params);
    }

    // 게시글 상세정보 조회
    @GetMapping("/{id}")
    public PostResponseDTO findPostById(@PathVariable final Long id) {
        return postService.findPostById(id);
    }

    // 게시글 목록 조회
    @GetMapping
    public List<PostResponseDTO> findAllPost() {
        return postService.findAllPost();
    }

    @DeleteMapping("/{id}/postDelete")
    private boolean deletePost(@PathVariable final Long id){
        return postService.deletePost(id);

    }


    // 비밀번호 검증
    @GetMapping("/{id}/verify-password")
    public Map<String, Boolean> verifyPassword(@PathVariable Long id, @RequestParam(name = "password") String password) {
        boolean valid = postService.verifyPassword(id, password);
        Map<String, Boolean> response = new HashMap<>();
        response.put("valid", valid);
        return response;
    }



    @PutMapping("/{id}")
    public String updateBoard(@PathVariable Long id) {

        boolean updated = postService.updatePost(id);
        if (updated) {
            return "게시물이 성공적으로 수정되었습니다.";
        } else {
            return "수정실패";
        }
    }





    // 게시글 저장
    @PostMapping("/notice")
    public Long saveNoticePost(@RequestBody final NoticeRequestDTO params) {
        return noticeService.saveNotice(params);
    }

    // 게시글 상세정보 조회
    @GetMapping("/notice/{id}")
    public NoticeResponseDTO findNoticePostById(@PathVariable final Long id) {
        return noticeService.findNoticeById(id);
    }

    // 게시글 목록 조회
    @GetMapping("/notice")
    public List<NoticeResponseDTO> findNoticeAllPost() {
        return noticeService.findAllNotice();
    }

    @DeleteMapping("/notice/delete/{id}")
    private boolean deleteNoticePost(@PathVariable final Long id){
        return noticeService.deleteNotice(id);

    }




}