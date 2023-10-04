package spring.project.groupware.academy.attendance;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AttendanceService{

    private final AttendanceRepository attendanceRepository;

    public static void inAttend(LocalDateTime now) {
    }

    public static void outAttend(LocalDateTime now) {
    }

    public static void sickAttend(LocalDateTime now) {
    }

    public static void vacationAttend(LocalDateTime now) {
    }

    public static void outingAttend(LocalDateTime now) {
    }

    public static void absentAttend(LocalDateTime now) {
    }
}
