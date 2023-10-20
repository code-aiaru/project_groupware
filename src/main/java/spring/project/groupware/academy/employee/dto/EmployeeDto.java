package spring.project.groupware.academy.employee.dto;

import lombok.*;
import spring.project.groupware.academy.employee.constraint.Role;
import spring.project.groupware.academy.employee.entity.EmployeeEntity;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class EmployeeDto {

    private Long employeeNo;

    @NotBlank
    @Size(min = 3, max = 20)
    private String employeeId;

    @NotBlank
    @Size(min = 3, max = 255)
//    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&^./,+=-])[A-Za-z\\d@$!%*#?&^./,+=-]{8,}$",
//            message = "최소 하나의 특수문자, 알파벳, 숫자를 포함해야 합니다")
    private String employeePassword;

    private String confirmPassword;

    @NotBlank
    @Size(min = 2, max = 20)
    @Pattern(regexp = "^[가-힣a-zA-Z]*$", message = "한글과 영문만 입력 가능합니다" )
    private String employeeName;

    private String employeeGender;

    @NotBlank
    @Size(min = 10, max = 11)
    @Pattern(regexp = "(01[016789])(\\d{3,4})(\\d{4})", message = "올바른 휴대전화번호를 입력해주세요")
    private String employeePhone;

    @NotBlank
    @Size(min = 10, max = 255)
    @Email
    private String employeeEmail;

    // emailId, domailCustom 합쳐서 저장
    private String emailId;

    private String domainCustom;

    @NotBlank
    private String employeeDep;

    @NotBlank
    private String employeePosition;

    // 생년월일(birthYear, birthMonth, birthDay 합쳐서 저장)
    private String employeeBirth;
    private int birthYear;
    private int birthMonth;
    private int birthDay;

    @NotBlank(message = "우편번호를 입력해주세요")
    private String employeePostCode;

    @NotBlank(message = "도로명주소를 입력해주세요")
    private String employeeStreetAddress;

    @NotBlank(message = "상세주소를 입력해주세요")
    private String employeeDetailAddress;

    private Role role;

    // 비밀번호 찾기 기능에서 이메일, 휴대전화번호 일치하는지 확인 시 필요
    private boolean matching;
    public EmployeeDto(boolean matching) {
        this.matching = matching;
    }

    public boolean isMatching(){ // 부에서 해당 멤버 변수 값을 읽을 수 있는 인터페이스를 제공하기 위해 사용
        return matching;
    }

    private String imageUrl;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    public static EmployeeDto toEmployeeDto(EmployeeEntity employeeEntity) {
        EmployeeDto employeeDto=new EmployeeDto();
        employeeDto.setEmployeeNo(employeeEntity.getEmployeeNo());
        employeeDto.setEmployeeId(employeeEntity.getEmployeeId());
        employeeDto.setEmployeePassword(employeeEntity.getEmployeePassword());
        employeeDto.setEmployeeName(employeeEntity.getEmployeeName());
        employeeDto.setEmployeeGender(employeeEntity.getEmployeeGender());
        employeeDto.setEmployeePhone(employeeEntity.getEmployeePhone());
        employeeDto.setEmployeeEmail(employeeEntity.getEmployeeEmail());
        employeeDto.setEmployeeDep(employeeEntity.getEmployeeDep());
        employeeDto.setEmployeePosition(employeeEntity.getEmployeePosition());
        employeeDto.setEmployeeBirth(employeeEntity.getEmployeeBirth());
        employeeDto.setEmployeePostCode(employeeEntity.getEmployeePostCode());
        employeeDto.setEmployeeStreetAddress(employeeEntity.getEmployeeStreetAddress());
        employeeDto.setEmployeeDetailAddress(employeeEntity.getEmployeeDetailAddress());
        employeeDto.setRole(employeeEntity.getRole());
        employeeDto.setCreateTime(employeeEntity.getCreateTime());
        employeeDto.setUpdateTime(employeeEntity.getUpdateTime());

//        employeeDto.setImageUrl(employeeEntity.getImage().getImageUrl());

        if (employeeEntity.getImage() != null && employeeEntity.getImage().getImageUrl() != null) {
            employeeDto.setImageUrl(employeeEntity.getImage().getImageUrl());
        }

        return employeeDto;
    }


}
