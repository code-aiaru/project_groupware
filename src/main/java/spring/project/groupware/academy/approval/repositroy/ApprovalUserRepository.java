package spring.project.groupware.academy.approval.repositroy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import spring.project.groupware.academy.approval.entity.ApprovalEntity;
import spring.project.groupware.academy.approval.entity.ApprovalUserEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApprovalUserRepository extends JpaRepository<ApprovalUserEntity, Long> {
    List<ApprovalUserEntity> findByApprovalEntity(ApprovalEntity approvalEntity);

    @Query(value = "select * "+
            "from Approval_user au " +
            "where au.approval_id = :apId and au.employee_no = :employeeNo", nativeQuery = true)
    Optional<ApprovalUserEntity> findByNo(Long apId, Long employeeNo);
}
