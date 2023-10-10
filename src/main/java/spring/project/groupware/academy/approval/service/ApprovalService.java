package spring.project.groupware.academy.approval.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import spring.project.groupware.academy.approval.dto.ApprovalDto;
import spring.project.groupware.academy.approval.entity.ApprovalEntity;
import spring.project.groupware.academy.approval.repositroy.ApprovalRepository;
import spring.project.groupware.academy.employee.entity.EmployeeEntity;
import spring.project.groupware.academy.employee.repository.EmployeeRepository;

@Service
@RequiredArgsConstructor
public class ApprovalService {

    private final EmployeeRepository employeeRepository;
    private final ApprovalRepository approvalRepository;

    public Long approvalWrite(ApprovalDto approvalDto, String employeeId) {
        EmployeeEntity employeeEntity = employeeRepository.findByEmployeeId(employeeId).orElseThrow(()->{
            throw new IllegalArgumentException("회원 존재하지 않음");
        });

        Long approvalId = approvalRepository.save(ApprovalEntity.builder()
                .ApprovalStatus("대기")
                .ApprovalTitle(approvalDto.getApprovalTitle())
                .ApprovalContent(approvalDto.getApprovalContent())
                .employeeEntity(employeeEntity)
                .build()).getId();

        return approvalId;
    }

    public Page<ApprovalDto> approvalListPage(Pageable pageable, String employeeId) {
        EmployeeEntity employeeEntity = employeeRepository.findByEmployeeId(employeeId).orElseThrow(()->{
            throw new IllegalArgumentException("회원 존재하지 않음");
        });

        Page<ApprovalEntity> approvalEntityPage = approvalRepository.findByEmplyeeEntityNo(pageable, employeeEntity.getEmployeeNo());
        Page<ApprovalDto> approvalDtoPage = approvalEntityPage.map(ApprovalDto::toapprovalDto);

        return approvalDtoPage;
    }

    public ApprovalDto approvalDetail(Long id) {
        ApprovalEntity aprrovalEntity = approvalRepository.findById(id).orElseThrow(()->{
            throw new IllegalArgumentException("결재문서 존재 하지않음");
        });
        ApprovalDto approvalDto = ApprovalDto.toapprovalDto(aprrovalEntity);
        return approvalDto;
    }
}
