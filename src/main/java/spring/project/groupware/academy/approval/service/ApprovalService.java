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
    // 로그인한 사람이 작성한 결재 리스트
    public Page<ApprovalDto> approvalCreateListPage(Pageable pageable, String employeeId) {
        EmployeeEntity employeeEntity = employeeRepository.findByEmployeeId(employeeId).orElseThrow(()->{
            throw new IllegalArgumentException("회원 존재하지 않음");
        });

        Page<ApprovalEntity> approvalEntityPage = approvalRepository.findByEmployeeEntityNo(pageable, employeeEntity.getEmployeeNo());
        Page<ApprovalDto> approvalDtoPage = approvalEntityPage.map(ApprovalDto::toapprovalDto);

        return approvalDtoPage;
    }
    // 로그인한 사람이 참조할수 있는 결재 리스트
    public Page<ApprovalDto> approvalReadListPage(Pageable pageable, String employeeId) {
        EmployeeEntity employeeEntity = employeeRepository.findByEmployeeId(employeeId).orElseThrow(()->{
            throw new IllegalArgumentException("회원 존재하지 않음");
        });
        String status="대기";
        Page<ApprovalEntity> approvalEntityPage = approvalRepository.findByReadList(pageable, employeeEntity.getEmployeeNo(), status);
        Page<ApprovalDto> approvalDtoPage=approvalEntityPage.map(ApprovalDto::toapprovalDto);

        return approvalDtoPage;
    }

    // 로그인한 사람이 결재 해야하는 리스트
    public Page<ApprovalDto> approvalResultListPage(Pageable pageable, String employeeId) {
        EmployeeEntity employeeEntity = employeeRepository.findByEmployeeId(employeeId).orElseThrow(()->{
            throw new IllegalArgumentException("회원 존재하지 않음");
        });
        Long ap=0L;
        String status="대기";
        Page<ApprovalEntity> approvalEntityPage = approvalRepository.findByApprovalList(pageable, employeeEntity.getEmployeeNo(), ap, status);
        Page<ApprovalDto> approvalDtoPage=approvalEntityPage.map(ApprovalDto::toapprovalDto);

        return approvalDtoPage;
    }

    public ApprovalDto approvalDetail(Long id) {
        ApprovalEntity approvalEntity = approvalRepository.findById(id).orElseThrow(()->{
            throw new IllegalArgumentException("결재문서 존재 하지않음");
        });
        ApprovalDto approvalDto = ApprovalDto.toapprovalDto(approvalEntity);
        return approvalDto;
    }

    public int approvalDelete(Long id) {
        ApprovalEntity approvalEntity = approvalRepository.findById(id).orElseThrow(()->{
            throw new IllegalArgumentException("결재문서 존재 하지않음");
        });
        approvalRepository.delete(approvalEntity);
        if (!approvalRepository.findById(id).isPresent()){
            return 1;
        }
        return 0;
    }

    public int approvalAp(ApprovalDto approvalDto) {
        ApprovalEntity approvalEntity = approvalRepository.findById(approvalDto.getId()).orElseThrow(()->{
            throw new IllegalArgumentException("결재문서 존재 하지않음");
        });
        Long approvalId = approvalRepository.save(approvalEntity.builder()
                .Id(approvalDto.getId())
                .ApprovalTitle(approvalEntity.getApprovalTitle())
                .ApprovalContent(approvalEntity.getApprovalContent())
                .ApprovalStatus(approvalDto.getApprovalStatus())
                .ApprovalAnswer(approvalDto.getApprovalAnswer())
                .approvalUserEntityList(approvalEntity.getApprovalUserEntityList())
                .employeeEntity(approvalEntity.getEmployeeEntity())
                .build()).getId();
        if (approvalId != null){
            return 1;
        }
        return 0;
    }

    public Long approvalUpdate(ApprovalDto approvalDto, String employeeId) {
        EmployeeEntity employeeEntity = employeeRepository.findByEmployeeId(employeeId).orElseThrow(()->{
            throw new IllegalArgumentException("회원 존재하지 않음");
        });

        Long approvalId = approvalRepository.save(ApprovalEntity.builder()
                .Id(approvalDto.getId())
                .ApprovalStatus("대기")
                .ApprovalTitle(approvalDto.getApprovalTitle())
                .ApprovalContent(approvalDto.getApprovalContent())
                .employeeEntity(employeeEntity)
                .build()).getId();

        return approvalId;
    }
}
