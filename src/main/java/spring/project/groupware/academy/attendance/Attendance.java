package spring.project.groupware.academy.attendance;

import lombok.*;
import spring.project.groupware.academy.employee.entity.EmployeeEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Table(name = "attendance")
public class Attendance {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime inAtt;
    private LocalDateTime outAtt;
    private LocalDate attDate;

    @Enumerated(EnumType.STRING)
    private AttendanceStatus attendanceStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private EmployeeEntity employee;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "student_id")
//    private StudentEntity student;

//    // 직원 쪽 엔티티에 추가
//    // 출석 연관 관계
//    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
//    private List<Attendance> attends = new ArrayList<>();





}
