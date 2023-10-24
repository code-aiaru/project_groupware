package spring.project.groupware.academy.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.project.groupware.academy.post.dto.PostRequestDTO;
import spring.project.groupware.academy.post.dto.PostResponseDTO;
import spring.project.groupware.academy.post.entity.Post;
import spring.project.groupware.academy.post.repository.PostRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    // 게시글 저장
    @Transactional
    public Long savePost(final PostRequestDTO params) {
        Post post = postRepository.save(params.toEntity());
        return post.getId();
    }

    // 게시글 상세정보 조회
    @Transactional(readOnly = true)
    public PostResponseDTO findPostById(final Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("post not found : " + id));
        return new PostResponseDTO(post);
    }

    // 게시글 목록 조회
    @Transactional(readOnly = true)
    public List<PostResponseDTO> findAllPost() {
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .map(post -> new PostResponseDTO(post))
                .collect(Collectors.toList());
    }

}