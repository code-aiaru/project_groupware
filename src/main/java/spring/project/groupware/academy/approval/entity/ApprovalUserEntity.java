package spring.project.groupware.academy.approval.entity;

import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "ApprovalUser")
public class ApprovalUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "approval_user_id")
    private Long id;

    private String Approval;

    @ManyToOne
    @JoinColumn(name = "approval_id")
    private ApprovalEntity approvalEntity;

//    @ManyToOne
//    @JoinColumn(name = "employee_no")
//    private EmployeeEntity employeeEntity;

}