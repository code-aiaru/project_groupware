package spring.project.groupware.academy.approval.repositroy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.project.groupware.academy.approval.entity.ApprovalEntity;

@Repository
public interface ApprovalRepository extends JpaRepository<ApprovalEntity, Long> {
}
