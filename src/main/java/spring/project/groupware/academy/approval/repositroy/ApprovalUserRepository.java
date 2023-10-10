package spring.project.groupware.academy.approval.repositroy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.project.groupware.academy.approval.entity.ApprovalEntity;
import spring.project.groupware.academy.approval.entity.ApprovalUserEntity;

import java.util.List;

@Repository
public interface ApprovalUserRepository extends JpaRepository<ApprovalUserEntity, Long> {
    List<ApprovalUserEntity> findByApprovalEntity(ApprovalEntity approvalEntity);
}
