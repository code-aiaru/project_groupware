package spring.project.groupware.academy.employee.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import spring.project.groupware.academy.employee.dto.EmployeeDto;
import spring.project.groupware.academy.employee.entity.EmployeeEntity;
import spring.project.groupware.academy.employee.entity.ImageEntity;
import spring.project.groupware.academy.employee.repository.EmployeeRepository;
import spring.project.groupware.academy.employee.repository.ImageRepository;


import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmployeeService {

    private final PasswordEncoder passwordEncoder;
    private final EmployeeRepository employeeRepository;
    private final ImageRepository imageRepository;

    
    // Create(사원 추가)
    @Transactional
    public void insertEmployee(EmployeeDto employeeDto) {

        EmployeeEntity employeeEntity = EmployeeEntity.toEmployeeEntityInsert(employeeDto, passwordEncoder);
        Long employeeNo = employeeRepository.save(employeeEntity).getEmployeeNo();

        // 이미지 생성 및 저장
        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setImageUrl("/employeeImages/default.png");
        imageEntity.setEmployee(employeeEntity);

        // ImageEntity를 db에 저장
        imageRepository.save(imageEntity);
    }

    //  Detail (to메서드)
    public EmployeeDto detailEmployee(Long employeeNo) {

        Optional<EmployeeEntity> optionalEmployeeEntity = Optional.ofNullable(employeeRepository.findById(employeeNo).orElseThrow(() -> {
            return new IllegalArgumentException("조회할 사원이 없습니다");
        }));

        if (optionalEmployeeEntity.isPresent()) {
//            ImageEntity imageEntity = optionalEmployeeEntity.get().getImage();
            return EmployeeDto.toEmployeeDto(optionalEmployeeEntity.get());
        }
        return null;
    }

    // UpdateView - 사원정보 수정화면
    public EmployeeDto updateViewEmployee(Long employeeNo) {
        EmployeeEntity employeeEntity = employeeRepository.findById(employeeNo).orElseThrow(IllegalAccessError::new);
        return EmployeeDto.toEmployeeDto(employeeEntity);
    }

    // Update - 사원정보 수정
    public int updateEmployee(EmployeeDto employeeDto) {

        Optional<EmployeeEntity> optionalEmployeeEntity = Optional.ofNullable(employeeRepository.findById(employeeDto.getEmployeeNo()).orElseThrow(() -> {
            return new IllegalArgumentException("수정할 사원정보가 없습니다");
        }));

        EmployeeEntity employeeEntity = EmployeeEntity.toEmployeeEntityUpdate(employeeDto);
        Long employeeNo = employeeRepository.save(employeeEntity).getEmployeeNo();

        Optional<EmployeeEntity> optionalEmployeeEntity1 = Optional.ofNullable(employeeRepository.findById(employeeNo).orElseThrow(() -> {
            return new IllegalArgumentException("수정할 사원정보가 없습니다");
        }));

        if (optionalEmployeeEntity1.isPresent()) {
            System.out.println("사원정보 수정 성공");
            return 1;

        } else {
            System.out.println("사원정보 수정 실패");
            return 0;
        }
    }

    // Read, 페이징/검색
    public Page<EmployeeDto> employeeList(Pageable pageable, String subject, String search) {

        Page<EmployeeEntity> employeeEntities = null; // 기본 null값으로 설정

        if(subject.equals("employeeName")){
            employeeEntities = employeeRepository.findByEmployeeNameContaining(pageable, search);
        }else if(subject.equals("employeePhone")){
            employeeEntities = employeeRepository.findByEmployeePhoneContaining(pageable, search);
        }else if(subject.equals("employeeDep")) {
            employeeEntities = employeeRepository.findByEmployeeDepContaining(pageable, search);
        }else if(subject.equals("employeePosition")) {
            employeeEntities = employeeRepository.findByEmployeePositionContaining(pageable, search);
        }else if(subject.equals("employeeEmail")) {
            employeeEntities = employeeRepository.findByEmployeeEmailContaining(pageable, search);
        }else{
            employeeEntities = employeeRepository.findAll(pageable);
        }

        employeeEntities.getNumber();
        employeeEntities.getTotalElements();
        employeeEntities.getTotalPages();
        employeeEntities.getSize();

        Page<EmployeeDto> employeeDtos = employeeEntities.map(EmployeeDto::toEmployeeDto);

        return employeeDtos;
    }

    // Delete
    public int deleteEmployee(Long employeeNo) {

        Optional<EmployeeEntity> optionalEmployeeEntity = Optional.ofNullable(employeeRepository.findById(employeeNo).orElseThrow(() -> {
            return new IllegalArgumentException("삭제할 사원번호가 없습니다");
        }));

        employeeRepository.delete(optionalEmployeeEntity.get());

        Optional<EmployeeEntity> optionalEmployeeEntity1 = employeeRepository.findById(employeeNo);

        if (!optionalEmployeeEntity1.isPresent()) {
            return 1;
        } else {
            return 0;
        }
    }

    // 아이디 찾기 메서드(이메일, 휴대전화번호 이용)
    public String findIdByEmailAndPhone(String employeeEmail, String employeePhone) {

        EmployeeEntity employeeEntity = employeeRepository.findByEmployeeEmailAndEmployeePhone(employeeEmail, employeePhone);
        if (employeeEntity != null) {
            return employeeEntity.getEmployeeId();
        }
        return null;
    }

    // 비밀번호 찾기 기능에서 이메일, 휴대전화번호 일치하는지 확인
    public boolean checkEmailPhoneMatching(String employeeEmail, String employeePhone) {

        EmployeeEntity employeeEntity = employeeRepository.findByEmployeeEmailAndEmployeePhone(employeeEmail, employeePhone);
        return employeeEntity != null; // 이메일과 휴대전화번호가 일치하면 memberEntity 객체가 반환되어서 null이 아님
    }

    // 임시 비밀번호 발급 관련 메서드
    public void SetTempPassword(String employeeEmail, String tempPassword) {

        Optional<EmployeeEntity> optionalEmployeeEntity = employeeRepository.findByEmployeeEmail(employeeEmail);

        if (optionalEmployeeEntity.isPresent()) {
            EmployeeEntity employeeEntity = optionalEmployeeEntity.get();
            String encodedPassword = passwordEncoder.encode(tempPassword);
            employeeEntity.setEmployeePassword(encodedPassword);
            employeeRepository.save(employeeEntity);
            log.info("이메일 주소에 임시 비밀번호가 설정되었습니다: {}", employeeEmail);
        } else {
            log.error("해당 이메일을 가진 회원을 찾을수없습니다: {}", employeeEmail);
        }
    }

    // 비밀번호 변경 전 현재 비밀번호 확인
    // 입력한 현재비밀번호와 DB에 있는 현재비밀번호 일치하는지 확인하는 메서드
    public boolean checkCurrentPassword(Long employeeNo, String currentPassword) {
        Optional<EmployeeEntity> optionalEmployeeEntity = employeeRepository.findById(employeeNo);

        if (optionalEmployeeEntity.isPresent()) {
            EmployeeEntity employeeEntity = optionalEmployeeEntity.get();
            return passwordEncoder.matches(currentPassword, employeeEntity.getEmployeePassword());
        }
        return false;
    }

    // 비밀번호 변경 메서드
    public boolean changePassword(Long employeeNo, String currentPassword, String newPassword, EmployeeDto employeeDto) {
        Optional<EmployeeEntity> optionalEmployeeEntity = employeeRepository.findById(employeeNo);

        if (optionalEmployeeEntity.isEmpty()) {
            throw new RuntimeException("해당 사원번호를 가진 회원을 찾을 수 없습니다.");
        }

        EmployeeEntity employeeEntity = optionalEmployeeEntity.get();

        // 현재 비밀번호 확인
        if (!passwordEncoder.matches(currentPassword, employeeEntity.getEmployeePassword())) {
            throw new RuntimeException("현재 비밀번호가 일치하지 않습니다.");
        }

        // 새로운 비밀번호 암호화
        String encryptedNewPassword = passwordEncoder.encode(newPassword);
        employeeEntity.setEmployeePassword(encryptedNewPassword);

        // 회원 정보 저장
        employeeRepository.save(employeeEntity);
        return true;
    }

    // 중복 검사 관련 메서드
    public boolean existsByEmployeeId(String employeeId) {
        return employeeRepository.existsByEmployeeId(employeeId);
    }

    // 중복 검사 관련 메서드
    public boolean existsByEmployeeEmail(String employeeEmail) {
        return employeeRepository.existsByEmployeeEmail(employeeEmail);
    }

    // 중복 검사 관련 메서드
    public boolean existsByEmployeePhone(String employeePhone) {
        return employeeRepository.existsByEmployeePhone(employeePhone);
    }



}
