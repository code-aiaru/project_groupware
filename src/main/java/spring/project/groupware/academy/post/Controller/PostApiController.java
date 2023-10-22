package spring.project.groupware.academy.post.Controller;



import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import spring.project.groupware.academy.post.dto.PostRequestDTO;
import spring.project.groupware.academy.post.dto.PostResponseDTO;
import spring.project.groupware.academy.post.service.PostService;

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

}