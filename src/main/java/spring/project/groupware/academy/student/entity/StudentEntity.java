package spring.project.groupware.academy.student.entity;

import lombok.*;
import spring.project.groupware.academy.attendance.entity.Attendance;
import spring.project.groupware.academy.employee.entity.ImageEntity;
import spring.project.groupware.academy.student.dto.StudentDto;
import spring.project.groupware.academy.util.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "student")
public class StudentEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "student_name", nullable = false)
    private String studentName;

    // 수강중인 강의명
    @Column(name = "student_class")
    private String studentClass;

    @Column(name = "student_phone", nullable = false, unique = true)
    private String studentPhone;

    @Column(name = "student_email", nullable = false, unique = true)
    private String studentEmail;

    @Column(name = "student_gender", nullable = false)
    private String studentGender;

    @Column(name = "student_birth")
    private String studentBirth;

    // 우편번호
    @Column(name = "student_postCode", nullable = false)
    private String studentPostCode;

    // 도로명주소
    @Column(name = "student_streetAddress")
    private String studentStreetAddress;

    // 상세주소
    @Column(name = "student_detailAddress")
    private String studentDetailAddress;
    
    // 수강생 상태(ex. 출석, 결석, 병가, 조퇴, 지각 등)
    @Column(name = "student_status")
    private String studentStatus;

    // 연관 관계 - image
    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL)
    private ImageEntity image;

    // 연관 관계 - attendance
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Attendance> attendance = new ArrayList<>();


    public static StudentEntity toStudentEntityInsert(StudentDto studentDto) {

        StudentEntity studentEntity = new StudentEntity();

        studentEntity.setStudentName(studentDto.getStudentName());
        studentEntity.setStudentClass(studentDto.getStudentClass());
        studentEntity.setStudentPhone(studentDto.getStudentPhone());
        studentEntity.setStudentEmail(studentDto.getStudentEmail());
        studentEntity.setStudentGender(studentDto.getStudentGender());
        studentEntity.setStudentBirth(studentDto.getStudentBirth());
        studentEntity.setStudentPostCode(studentDto.getStudentPostCode());
        studentEntity.setStudentStreetAddress(studentDto.getStudentStreetAddress());
        studentEntity.setStudentDetailAddress(studentDto.getStudentDetailAddress());

        return studentEntity;
    }

    public static StudentEntity toStudentEntityUpdate(StudentDto studentDto) {

        StudentEntity studentEntity = new StudentEntity();

        studentEntity.setStudentId(studentDto.getStudentId());
        studentEntity.setStudentName(studentDto.getStudentName());
        studentEntity.setStudentClass(studentDto.getStudentClass());
        studentEntity.setStudentPhone(studentDto.getStudentPhone());
        studentEntity.setStudentEmail(studentDto.getStudentEmail());
        studentEntity.setStudentGender(studentDto.getStudentGender());
        studentEntity.setStudentBirth(studentDto.getStudentBirth());
        studentEntity.setStudentPostCode(studentDto.getStudentPostCode());
        studentEntity.setStudentStreetAddress(studentDto.getStudentStreetAddress());
        studentEntity.setStudentDetailAddress(studentDto.getStudentDetailAddress());
        studentEntity.setStudentStatus(studentDto.getStudentStatus());
        studentEntity.setCreateTime(studentDto.getCreateTime());
        studentEntity.setUpdateTime(studentDto.getUpdateTime());

        return studentEntity;
    }


}
