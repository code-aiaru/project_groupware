package spring.project.groupware.academy.post.Controller;



import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.project.groupware.academy.board.dto.BoardDto;
import spring.project.groupware.academy.post.dto.PostRequestDTO;
import spring.project.groupware.academy.post.dto.PostResponseDTO;
import spring.project.groupware.academy.post.service.PostService;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;

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

    @PutMapping("/{id}")
    public String updateBoard(@PathVariable Long id, @RequestBody PostResponseDTO postResponseDTO , HttpSession session) {

        boolean updated = postService.updatePost(id, postResponseDTO);
        if (updated) {
            session.removeAttribute("editPageId");
            session.removeAttribute("clientPassword");
            return "게시물이 성공적으로 수정되었습니다.";
        } else {
            return "게시물 수정에 실패했습니다. 비밀번호가 일치하지 않거나 게시물이 존재하지 않습니다.";
        }
    }



}