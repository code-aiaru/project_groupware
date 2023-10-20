package spring.project.groupware.academy.student.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import spring.project.groupware.academy.employee.dto.EmployeeDto;
import spring.project.groupware.academy.employee.entity.EmployeeEntity;
import spring.project.groupware.academy.employee.entity.ImageEntity;
import spring.project.groupware.academy.employee.repository.ImageRepository;
import spring.project.groupware.academy.student.dto.StudentDto;
import spring.project.groupware.academy.student.entity.StudentEntity;
import spring.project.groupware.academy.student.repository.StudentRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final ImageRepository imageRepository;

    // Create(학생 추가)
    @Transactional
    public void insertStudent(StudentDto studentDto) {

        StudentEntity studentEntity = StudentEntity.toStudentEntityInsert(studentDto);
        studentRepository.save(studentEntity).getStudentId();

        // 이미지 생성 및 저장
        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setImageUrl("/studentImages/default.png");
        imageEntity.setStudent(studentEntity);

        // ImageEntity를 db에 저장
        imageRepository.save(imageEntity);
    }

    // Detail
    public StudentDto detailStudent(Long studentId) {

        Optional<StudentEntity> optionalStudentEntity = Optional.ofNullable(studentRepository.findById(studentId).orElseThrow(() -> {
            return new IllegalArgumentException("조회할 사원이 없습니다");
        }));

        if (optionalStudentEntity.isPresent()) {
            return StudentDto.toStudentDto(optionalStudentEntity.get());
        }
        return null;
    }

    // UpdateView - 학생정보 수정화면
    public StudentDto updateViewStudent(Long studentId) {
        StudentEntity studentEntity = studentRepository.findById(studentId).orElseThrow(IllegalAccessError::new);
        return StudentDto.toStudentDto(studentEntity);
    }

    // Update - 학생정보 수정
    public int updateStudent(StudentDto studentDto) {

        Optional<StudentEntity> optionalStudentEntity = Optional.ofNullable(studentRepository.findById(studentDto.getStudentId()).orElseThrow(() -> {
            return new IllegalArgumentException("수정할 학생정보가 없습니다");
        }));

        StudentEntity studentEntity = StudentEntity.toStudentEntityUpdate(studentDto);
        Long studentId = studentRepository.save(studentEntity).getStudentId();

        Optional<StudentEntity> optionalStudentEntity1 = Optional.ofNullable(studentRepository.findById(studentId).orElseThrow(() -> {
            return new IllegalArgumentException("수정할 학생정보가 없습니다");
        }));

        if (optionalStudentEntity1.isPresent()) {
            System.out.println("학생정보 수정 성공");
            return 1;

        } else {
            System.out.println("학생정보 수정 실패");
            return 0;
        }
    }

    // Read, 페이징/ 검색
    public Page<StudentDto> studentList(Pageable pageable, String subject, String search) {

        Page<StudentEntity> studentEntities = null;

        if(subject.equals("studentName")){
            studentEntities = studentRepository.findByStudentNameContaining(pageable, search);
        }else if(subject.equals("studentClass")){
            studentEntities = studentRepository.findByStudentClassContaining(pageable, search);
        }else if(subject.equals("studentPhone")) {
            studentEntities = studentRepository.findByStudentPhoneContaining(pageable, search);
        }else if(subject.equals("studentEmail")) {
            studentEntities = studentRepository.findByStudentEmailContaining(pageable, search);
        }else if(subject.equals("studentStreetAddress")) {
            studentEntities = studentRepository.findByStudentStreetAddressContaining(pageable, search);
        }else{
            studentEntities = studentRepository.findAll(pageable);
        }

        studentEntities.getNumber();
        studentEntities.getTotalElements();
        studentEntities.getTotalPages();
        studentEntities.getSize();

        Page<StudentDto> studentDtos = studentEntities.map(StudentDto::toStudentDto);

        return studentDtos;
    }

    // Delete
    public int deleteStudent(Long studentId) {

        Optional<StudentEntity> optionalStudentEntity = Optional.ofNullable(studentRepository.findById(studentId).orElseThrow(() -> {
            return new IllegalArgumentException("삭제할 학생정보가 없습니다");
        }));

        studentRepository.delete(optionalStudentEntity.get());

        Optional<StudentEntity> optionalStudentEntity1 = studentRepository.findById(studentId);

        if (!optionalStudentEntity1.isPresent()) {
            return 1;
        } else {
            return 0;
        }
    }



}
