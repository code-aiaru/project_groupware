package spring.project.groupware.academy.student.dto;

import lombok.*;
import spring.project.groupware.academy.employee.entity.ImageEntity;
import spring.project.groupware.academy.student.entity.StudentEntity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StudentDto {

    private Long studentId;

    @NotBlank
    private String studentName;

    private String studentClass;

    @NotBlank
    private String studentPhone;

    @NotBlank
    private String studentEmail;

    private String studentGender;

    @NotBlank
    private String studentBirth;
    private int birthYear;
    private int birthMonth;
    private int birthDay;

    @NotBlank
    private String studentPostCode;

    private String studentStreetAddress;

    private String studentDetailAddress;

    private String imageUrl;

    private String studentStatus;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    public static StudentDto toStudentDto(StudentEntity studentEntity) {
        StudentDto studentDto = new StudentDto();
        studentDto.setStudentId(studentEntity.getStudentId());
        studentDto.setStudentName(studentEntity.getStudentName());
        studentDto.setStudentClass(studentEntity.getStudentClass());
        studentDto.setStudentPhone(studentEntity.getStudentPhone());
        studentDto.setStudentEmail(studentEntity.getStudentEmail());
        studentDto.setStudentGender(studentEntity.getStudentGender());
        studentDto.setStudentBirth(studentEntity.getStudentBirth());
        studentDto.setStudentPostCode(studentEntity.getStudentPostCode());
        studentDto.setStudentStreetAddress(studentEntity.getStudentStreetAddress());
        studentDto.setStudentDetailAddress(studentEntity.getStudentDetailAddress());
        studentDto.setStudentStatus(studentEntity.getStudentStatus());
        studentDto.setCreateTime(studentEntity.getCreateTime());
        studentDto.setUpdateTime(studentEntity.getUpdateTime());

        if (studentEntity.getImage() != null && studentEntity.getImage().getImageUrl() != null) {
            studentDto.setImageUrl(studentEntity.getImage().getImageUrl());
        }

        return studentDto;
    }

}
