package spring.project.groupware.academy.post.Controller;



import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.project.groupware.academy.post.dto.NoticeRequestDTO;
import spring.project.groupware.academy.post.dto.NoticeResponseDTO;
import spring.project.groupware.academy.post.dto.PostRequestDTO;
import spring.project.groupware.academy.post.dto.PostResponseDTO;
import spring.project.groupware.academy.post.entity.Notice;
import spring.project.groupware.academy.post.entity.Post;
import spring.project.groupware.academy.post.repository.PostRepository;
import spring.project.groupware.academy.post.service.NoticeService;
import spring.project.groupware.academy.post.service.PostService;
import spring.project.groupware.academy.post.service.RandomNameGenerator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@Slf4j
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostApiController {
    private final PostService postService;
    private final NoticeService noticeService;
    private final RandomNameGenerator randomNameGenerator;
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


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id, @RequestParam String password) {
        boolean isDeleted = postService.deletePost(id, password);

        if (isDeleted) {
            return ResponseEntity.ok("게시물 삭제 성공");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("비밀번호가 일치하지 않습니다.");
        }
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


    /*공지사항 게시판*/

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
    // 공지사항 리스트
    @GetMapping("/notice")
    public List<NoticeResponseDTO> findNoticeAllPost() {
        return noticeService.findAllNotice();
    }

    @DeleteMapping("/notice/delete/{id}")
    private boolean deleteNoticePost(@PathVariable final Long id){
        return noticeService.deleteNotice(id);

    }

    
    
    // 랜덤이름
    @GetMapping("/getRandomName")
    public Map<String, String> getRandomName() {
        Map<String, String> response = new HashMap<>();
        response.put("randomName", randomNameGenerator.getRandomName());
        return response;
    }


}