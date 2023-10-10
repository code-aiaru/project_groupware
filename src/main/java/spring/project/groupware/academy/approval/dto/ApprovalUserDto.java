package spring.project.groupware.academy.approval.dto;

import lombok.*;
import spring.project.groupware.academy.approval.entity.ApprovalEntity;
import spring.project.groupware.academy.approval.entity.ApprovalUserEntity;
import spring.project.groupware.academy.employee.entity.EmployeeEntity;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ApprovalUserDto {

    private Long Id;
    private Long Ap;
    private ApprovalEntity approvalEntity;
    private EmployeeEntity employeeEntity;

    public static ApprovalUserDto toPayuserDto(ApprovalUserEntity approvalUserEntity) {
        ApprovalUserDto approvalUserDto = new ApprovalUserDto();
        approvalUserDto.setId(approvalUserEntity.getId());
        approvalUserDto.setAp(approvalUserEntity.getAp());
        approvalUserDto.setEmployeeEntity(approvalUserEntity.getEmployeeEntity());
        approvalUserDto.setApprovalEntity(approvalUserEntity.getApprovalEntity());

        return approvalUserDto;
    }
}
