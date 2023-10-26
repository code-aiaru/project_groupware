package spring.project.groupware.academy.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.project.groupware.academy.post.dto.PostRequestDTO;
import spring.project.groupware.academy.post.dto.PostResponseDTO;
import spring.project.groupware.academy.post.entity.Post;
import spring.project.groupware.academy.post.repository.PostRepository;

import java.util.List;
import java.util.Optional;
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

    @Transactional(readOnly = true)
    public List<PostResponseDTO> findAllPost() {
        List<Post> posts= postRepository.findAll();
        return posts.stream()
                .map(PostResponseDTO::new)
                .collect(Collectors.toList());
    }
    @Transactional
    public boolean deletePost(Long id, String password) {

        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            if (post.verifyPassword(password)) {
                postRepository.delete(post);
                return true;
            } else {
                return false;
            }

        }
        return false;
    }

    @Transactional
    public boolean updatePost(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        PostResponseDTO postResponseDTO = new PostResponseDTO();

        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            post.setTitle(postResponseDTO.getTitle());
            post.setContent(postResponseDTO.getContent());

            Post updatePost = postRepository.save(post);
            return true;
        } else {
            return false;
        }


    }


}

