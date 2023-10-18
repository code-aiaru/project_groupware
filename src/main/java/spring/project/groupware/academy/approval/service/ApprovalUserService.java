package spring.project.groupware.academy.approval.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import spring.project.groupware.academy.approval.dto.ApprovalDto;
import spring.project.groupware.academy.approval.dto.ApprovalUserDto;
import spring.project.groupware.academy.approval.entity.ApprovalEntity;
import spring.project.groupware.academy.approval.entity.ApprovalUserEntity;
import spring.project.groupware.academy.approval.repositroy.ApprovalRepository;
import spring.project.groupware.academy.approval.repositroy.ApprovalUserRepository;
import spring.project.groupware.academy.employee.entity.EmployeeEntity;
import spring.project.groupware.academy.employee.repository.EmployeeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ApprovalUserService  {

    private final ApprovalRepository approvalRepository;
    private final ApprovalUserRepository approvalUserRepository;
    private final EmployeeRepository employeeRepository;

    public void approvalUserCreate(List<ApprovalUserDto> approvalUserDtoList, Long approvalId) {
        for(ApprovalUserDto approvalUserDto : approvalUserDtoList){
            ApprovalEntity approvalEntity = approvalRepository.findById(approvalId).orElseThrow(()->{
                throw new IllegalArgumentException("결제메일 존재하지 않음");
            });
            EmployeeEntity employeeEntity = employeeRepository.findById(approvalUserDto.getId()).orElseThrow(()->{
                throw new IllegalArgumentException("회원 존재하지 않음");
            });
            approvalUserRepository.save(ApprovalUserEntity.builder()
                    .Ap(approvalUserDto.getAp())
                    .approvalEntity(approvalEntity)
                    .employeeEntity(employeeEntity)
                    .build());
        }
    }

    public List<ApprovalUserDto> approvalUserList(Long id) {
        List<ApprovalUserDto> approvalUserDtoList = new ArrayList<>();
        Optional<ApprovalEntity> optionalApprovalEntity = approvalRepository.findById(id);
        List<ApprovalUserEntity> approvalUserEntityList = approvalUserRepository.findByApprovalEntity(optionalApprovalEntity.get());
        for(ApprovalUserEntity approvalUserEntity: approvalUserEntityList){
            ApprovalUserDto approvalUserDto = ApprovalUserDto.toPayuserDto(approvalUserEntity);
            approvalUserDtoList.add(approvalUserDto);
        }
        return approvalUserDtoList;
    }

    public void approvalUserUpdate(List<ApprovalUserDto> approvalUserDtoList, Long approvalId) {
        for(ApprovalUserDto approvalUserDto : approvalUserDtoList){
            ApprovalEntity approvalEntity = approvalRepository.findById(approvalId).orElseThrow(()->{
                throw new IllegalArgumentException("결제메일 존재하지 않음");
            });
            EmployeeEntity employeeEntity = employeeRepository.findById(approvalUserDto.getId()).orElseThrow(()->{
                throw new IllegalArgumentException("회원 존재하지 않음");
            });
            ApprovalUserEntity approvalUserEntity = approvalUserRepository.findByNo(approvalEntity.getId(), employeeEntity.getEmployeeNo()).orElseThrow(()->{
                throw new IllegalArgumentException("결재선 존재하지 않음");
            });

            approvalUserRepository.save(ApprovalUserEntity.builder()
                    .id(approvalUserEntity.getId())
                    .Ap(approvalUserDto.getAp())
                    .approvalEntity(approvalEntity)
                    .employeeEntity(employeeEntity)
                    .build());
        }
    }
}
