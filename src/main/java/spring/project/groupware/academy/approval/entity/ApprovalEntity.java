package spring.project.groupware.academy.approval.entity;

import lombok.*;
import spring.project.groupware.academy.employee.entity.EmployeeEntity;
import spring.project.groupware.academy.util.BaseEntity;

import javax.persistence.*;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Approval")
public class ApprovalEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "approval_id")
    private Long Id;

    private String ApprovalStatus;
    private String ApprovalTitle;
    private String ApprovalContent;
    private String ApprovalAnswer;

    @OneToMany(mappedBy = "approvalEntity", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<ApprovalUserEntity> approvalUserEntityList;

    @ManyToOne
    @JoinColumn(name = "employee_no")
    private EmployeeEntity employeeEntity;

}
