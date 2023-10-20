package spring.project.groupware.academy.employee.entity;

import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import spring.project.groupware.academy.approval.entity.ApprovalEntity;
import spring.project.groupware.academy.approval.entity.ApprovalUserEntity;
import spring.project.groupware.academy.attendance.entity.Attendance;
import spring.project.groupware.academy.employee.constraint.Role;
import spring.project.groupware.academy.employee.dto.EmployeeDto;
import spring.project.groupware.academy.util.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "employee")
public class EmployeeEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_no")
    private Long employeeNo;

    @Column(name = "employee_id", nullable = false, unique = true)
    private String employeeId;

    @Column(name = "employee_password", nullable = false)
    private String employeePassword;

    @Column(name = "employee_name", nullable = false)
    private String employeeName;

    @Column(name = "employee_gender", nullable = false)
    private String employeeGender;

    @Column(name = "employee_phone", unique = true, nullable = false)
    private String employeePhone;

    @Column(name = "employee_email", nullable = false, unique = true)
    private String employeeEmail;

    // 부서
    @Column(name = "employee_dep", nullable = false)
    private String employeeDep;

    // 직급
    @Column(name = "employee_position", nullable = false)
    private String employeePosition;

    @Column(name = "employee_birth")
    private String employeeBirth;

    // 우편번호(주소 api 이용 위해 필요)
    @Column(name = "employee_postCode", nullable = false)
    private String employeePostCode;

    // 도로명주소(주소 api 이용 위해 필요)
    @Column(name = "employee_streetAddress")
    private String employeeStreetAddress;

    // 상세주소(주소 api 이용 위해 필요)
    @Column(name = "employee_detailAddress")
    private String employeeDetailAddress;

    @Enumerated(EnumType.STRING)
    private Role role;

    // 임시비밀번호
    @Column(name = "temporary_password")
    private String temporaryPassword;

    // 연관 관계 - image
    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    private ImageEntity image;

    // 연관 관계 - attendance
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Attendance> attendance = new ArrayList<>();

    // 연관 관계 - approval
    @OneToMany(mappedBy = "employeeEntity", cascade = CascadeType.ALL)
    private List<ApprovalEntity> approval = new ArrayList<>();

    // 연관 관계 - approvalUser
    @OneToMany(mappedBy = "employeeEntity", cascade = CascadeType.ALL)
    private List<ApprovalUserEntity> approvalUser = new ArrayList<>();

    // 연관 관계 - 일정 1:N



    public static EmployeeEntity toEmployeeEntityInsert(EmployeeDto employeeDto, PasswordEncoder passwordEncoder) {

        EmployeeEntity employeeEntity =new EmployeeEntity();

        employeeEntity.setEmployeeId(employeeDto.getEmployeeId());
        employeeEntity.setEmployeePassword(passwordEncoder.encode(employeeDto.getEmployeePassword()));
        employeeEntity.setEmployeeName(employeeDto.getEmployeeName());
        employeeEntity.setEmployeeGender(employeeDto.getEmployeeGender());
        employeeEntity.setEmployeePhone(employeeDto.getEmployeePhone());
        employeeEntity.setEmployeeEmail(employeeDto.getEmployeeEmail());
        employeeEntity.setEmployeeDep(employeeDto.getEmployeeDep());
        employeeEntity.setEmployeePosition(employeeDto.getEmployeePosition());
        employeeEntity.setEmployeeBirth(employeeDto.getEmployeeBirth());
        employeeEntity.setEmployeePostCode(employeeDto.getEmployeePostCode());
        employeeEntity.setEmployeeStreetAddress(employeeDto.getEmployeeStreetAddress());
        employeeEntity.setEmployeeDetailAddress(employeeDto.getEmployeeDetailAddress());
        employeeEntity.setRole(Role.ADMIN);
//        employeeEntity.setRole(employeeDto.getRole());

        return employeeEntity;
    }
    public static EmployeeEntity toEmployeeEntityUpdate(EmployeeDto employeeDto) {

        EmployeeEntity employeeEntity =new EmployeeEntity();

        employeeEntity.setEmployeeNo(employeeDto.getEmployeeNo());
        employeeEntity.setEmployeeId(employeeDto.getEmployeeId());
        employeeEntity.setEmployeePassword(employeeDto.getEmployeePassword());
        employeeEntity.setEmployeeName(employeeDto.getEmployeeName());
        employeeEntity.setEmployeeGender(employeeDto.getEmployeeGender());
        employeeEntity.setEmployeePhone(employeeDto.getEmployeePhone());
        employeeEntity.setEmployeeEmail(employeeDto.getEmployeeEmail());
        employeeEntity.setEmployeeDep(employeeDto.getEmployeeDep());
        employeeEntity.setEmployeePosition(employeeDto.getEmployeePosition());
        employeeEntity.setEmployeeBirth(employeeDto.getEmployeeBirth());
        employeeEntity.setEmployeePostCode(employeeDto.getEmployeePostCode());
        employeeEntity.setEmployeeStreetAddress(employeeDto.getEmployeeStreetAddress());
        employeeEntity.setEmployeeDetailAddress(employeeDto.getEmployeeDetailAddress());
        employeeEntity.setRole(employeeDto.getRole());
        employeeEntity.setCreateTime(employeeDto.getCreateTime());
        employeeEntity.setUpdateTime(employeeDto.getUpdateTime());

        return employeeEntity;
    }


}
