package spring.project.groupware.academy.student.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import spring.project.groupware.academy.student.entity.StudentEntity;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<StudentEntity, Long> {


    StudentEntity findByStudentId(Long studentId);

    Page<StudentEntity> findByStudentNameContaining(Pageable pageable, String studentName);

    Page<StudentEntity> findByStudentClassContaining(Pageable pageable, String studentClass);

    Page<StudentEntity> findByStudentPhoneContaining(Pageable pageable, String studentPhone);

    Page<StudentEntity> findByStudentEmailContaining(Pageable pageable, String studentEmail);

    Page<StudentEntity> findByStudentStreetAddressContaining(Pageable pageable, String studentStreetAddress);
}
