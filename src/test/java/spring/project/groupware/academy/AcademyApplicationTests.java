package spring.project.groupware.academy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import spring.project.groupware.academy.attendance.entity.Attendance;
import spring.project.groupware.academy.attendance.entity.AttendanceStatus;
import spring.project.groupware.academy.attendance.repository.AttendanceRepository;
import spring.project.groupware.academy.attendance.service.AttendanceService;
import spring.project.groupware.academy.employee.constraint.Role;
import spring.project.groupware.academy.employee.dto.EmployeeDto;
import spring.project.groupware.academy.employee.entity.EmployeeEntity;
import spring.project.groupware.academy.employee.repository.EmployeeRepository;

import java.time.LocalDate;

@SpringBootTest
class AcademyApplicationTests {

	//
	// 근태
	@Autowired
	private AttendanceRepository attendanceRepository;
	@Autowired
	private AttendanceService attendanceService;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Test
	void contextLoads() {

		// 임시계정 생성(admin)
//		EmployeeDto tempEmployeeDto = EmployeeDto.builder()
//				.employeeId("admin")
//				.employeePassword("111")
//				.employeeName("관리자")
//				.employeePhone("01012341234")
//				.employeeEmail("admin@email.com")
//				.employeeDep("총무부")
//				.employeePosition("원장")
//				.employeeBirth("21001231")
//				.employeePostCode("12345")
//				.employeeStreetAddress("도로명주소")
//				.employeeDetailAddress("상세주소")
//				.role(Role.ADMIN)
//				.build();

		// 임시계정을 Entity로 변환
//		EmployeeEntity tempEmployeeEntity = EmployeeEntity.toEmployeeEntityInsert(tempEmployeeDto, passwordEncoder);

		// Entity를 저장
//		employeeRepository.save(tempEmployeeEntity);




// 한 사원 한달치 확인용 생성
		EmployeeEntity emp99= employeeRepository.save(
				EmployeeEntity.builder()
						.employeeId("lee9")                                                                //
						.employeePassword("1111")
						.employeeName("이사원")
						.employeePhone("01033339999")        //
						.employeeEmail("lee9@email.com")                                //
						.employeeDep("총무부")
						.employeePosition("사원")
						.employeeBirth("21001231")
						.employeePostCode("12346")
						.employeeStreetAddress("도로명주소")
						.employeeDetailAddress("상세주소")
						.role(Role.EMPLOYEE)
						.build());

		for (int i=0;i<30;i++) {

			AttendanceStatus status = AttendanceStatus.ABSENT;
			attendanceRepository.save(
					Attendance.builder()
							.attDate(LocalDate.of(2023,10,LocalDate.MAX.getDayOfMonth()).minusDays(i))
							.employee(emp99)
							.attendanceStatus(status)
							.build());
		}

	}



}
