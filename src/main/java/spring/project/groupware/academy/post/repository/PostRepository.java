package spring.project.groupware.academy.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.project.groupware.academy.post.entity.Post;
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}