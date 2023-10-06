package spring.project.groupware.academy.attendance;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
//@Builder
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Attendance {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime inAtt;
    private LocalDateTime outAtt;
    private LocalDate attDate;

    @Enumerated(EnumType.STRING)
    private AttendanceStatus attendanceStatus;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "employee_id")
//    private Employee employee;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "student_id")
//    private Student student;

// 직원 쪽은
//    @OneToMany(mappedBy = "employee_id", cascade = CascadeType.ALL)
//    private List<Attend> attends = new ArrayList<>();





}
