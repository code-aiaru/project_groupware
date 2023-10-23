package spring.project.groupware.academy.approval.repositroy;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import spring.project.groupware.academy.approval.entity.ApprovalEntity;
import spring.project.groupware.academy.employee.entity.EmployeeEntity;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ApprovalRepository extends JpaRepository<ApprovalEntity, Long> {
    @Query(value = "select * "+
            "from Approval ap " +
            "where ap.employee_no = :employeeNo order by ap.approval_id desc", nativeQuery = true)
    Page<ApprovalEntity> findByEmployeeEntityNo(Pageable pageable, @Param("employeeNo") Long employeeNo);

    @Query(value = "select * "+
            "from Approval ap inner join Approval_user au " +
            "on	ap.approval_id = au.approval_id " +
            "where au.employee_no = :employeeNo and ap.approval_status NOT IN (:status) order by ap.approval_id desc", nativeQuery = true)
    Page<ApprovalEntity> findByReadList(Pageable pageable, Long employeeNo, String status);

    @Query(value = "select * "+
            "from Approval ap inner join Approval_user au " +
            "on	ap.approval_id = au.approval_id " +
            "where au.ap = :ap and au.employee_no = :employeeNo and ap.approval_status = :status order by ap.approval_id desc", nativeQuery = true)
    Page<ApprovalEntity> findByApprovalList(Pageable pageable, Long employeeNo, Long ap, String status);










    // 박상재 - 추가
    @Query("SELECT COUNT(a) FROM ApprovalEntity a " +
            "WHERE a.ApprovalStatus = :status AND a.employeeEntity = :employee")
    Long countByApprovalStatusAndEmployeeEntity(@Param("status") String ApprovalStatus,
                                                @Param("employee") EmployeeEntity employeeEntity);

    @Query("SELECT COUNT(a) FROM ApprovalEntity a " +
            "WHERE a.ApprovalStatus = :status AND a.employeeEntity = :employee " +
            "AND DATE(a.updateTime) = CURRENT_DATE")
    Long countByApprovedApprovalAndEmployeeEntity(@Param("status") String ApprovalStatus,
                                                  @Param("employee") EmployeeEntity employeeEntity);





}
