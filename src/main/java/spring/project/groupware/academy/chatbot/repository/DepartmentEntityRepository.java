package spring.project.groupware.academy.chatbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.project.groupware.academy.chatbot.entity.DepartmentEntity;

import java.util.List;
import java.util.Optional;

public interface DepartmentEntityRepository extends JpaRepository<DepartmentEntity, Long>{

	Optional<DepartmentEntity> findByDname(String dname);

	List<DepartmentEntity> findAllByDepth(int depth);

	Optional<DepartmentEntity> findAllByDepthAndDname(int i, String dname);

}
