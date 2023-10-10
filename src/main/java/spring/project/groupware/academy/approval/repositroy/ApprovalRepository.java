package spring.project.groupware.academy.approval.repositroy;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import spring.project.groupware.academy.approval.entity.ApprovalEntity;

@Repository
public interface ApprovalRepository extends JpaRepository<ApprovalEntity, Long> {
    @Query(value = "select * "+
            "from Approval ap " +
            "where ap.employee_no = :employeeNo order by ap.approval_id desc", nativeQuery = true)
    Page<ApprovalEntity> findByEmplyeeEntityNo(Pageable pageable, @Param("employeeNo") Long employeeNo);
}
