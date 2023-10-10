package spring.project.groupware.academy.approval.dto;

import lombok.*;
import spring.project.groupware.academy.approval.entity.ApprovalEntity;
import spring.project.groupware.academy.approval.entity.ApprovalUserEntity;
import spring.project.groupware.academy.employee.entity.EmployeeEntity;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ApprovalDto {

    private Long Id;
    private String ApprovalStatus;
    private String ApprovalTitle;
    private String ApprovalContent;
    private String ApprovalAnswer;
    private List<ApprovalUserEntity> approvalUserEntityList;
    private EmployeeEntity employeeEntity;
    private Long[] dataArray;
    private ApprovalUserDto[] ApprovalUserArray;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public static ApprovalDto toapprovalDto(ApprovalEntity approvalEntity) {
        ApprovalDto approvalDto = ApprovalDto.builder()
                .Id(approvalEntity.getId())
                .ApprovalStatus(approvalEntity.getApprovalStatus())
                .ApprovalTitle(approvalEntity.getApprovalTitle())
                .ApprovalContent(approvalEntity.getApprovalContent())
                .ApprovalAnswer(approvalEntity.getApprovalAnswer())
                .employeeEntity(approvalEntity.getEmployeeEntity())
                .approvalUserEntityList(approvalEntity.getApprovalUserEntityList())
                .createTime(approvalEntity.getCreateTime())
                .updateTime(approvalEntity.getUpdateTime())
                .build();
        return approvalDto;
    }
}
