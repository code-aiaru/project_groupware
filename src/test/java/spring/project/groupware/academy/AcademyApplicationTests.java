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
import spring.project.groupware.academy.salary.repository.SalaryRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

//	@Autowired
//	private EmployeeRepository employeeRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Test
	void admin() {

		// 임시계정 생성(admin)
		EmployeeDto tempEmployeeDto = EmployeeDto.builder()
				.employeeId("admin")
				.employeePassword("111")
				.employeeName("관리자")
				.employeeGender("남")
				.employeePhone("01012341234")
				.employeeEmail("admin@email.com")
				.employeeDep("총무부")
				.employeePosition("원장")
				.employeeBirth("21001231")
				.employeePostCode("12345")
				.employeeStreetAddress("도로명주소")
				.employeeDetailAddress("상세주소")
				.role(Role.ADMIN)
				.build();

//		 임시계정을 Entity로 변환
		EmployeeEntity tempEmployeeEntity = EmployeeEntity.toEmployeeEntityInsert(tempEmployeeDto, passwordEncoder);

//		 Entity를 저장
		employeeRepository.save(tempEmployeeEntity);
	}
	
	// gpt, 임시계정 10개 생성
	@Test
	void createUniqueAccounts() {
		List<String> existingIds = new ArrayList<>();
		List<String> existingPhones = new ArrayList<>();
		List<String> existingEmails = new ArrayList();

		// 먼저 기존 데이터베이스에 있는 아이디, 휴대전화 및 이메일 목록을 가져옵니다.
		List<EmployeeEntity> existingEmployees = employeeRepository.findAll();
		for (EmployeeEntity entity : existingEmployees) {
			existingIds.add(entity.getEmployeeId());
			existingPhones.add(entity.getEmployeePhone());
			existingEmails.add(entity.getEmployeeEmail());
		}

		int createdAccounts = 0;
		int userNumber = 1;

		while (createdAccounts < 10) {
			// 아이디, 휴대전화, 이메일 생성
			String employeeId = generateUniqueEmployeeId(existingIds);
			String phone = generateUniquePhone(existingPhones);
			String email = generateUniqueEmail(existingEmails);

			// 이름에 숫자를 추가하여 계정 생성
			String employeeName = "임시 사용자 " + userNumber;

			// 나머지 필드에는 랜덤한 값을 부여하거나 기본값을 사용합니다.
			EmployeeDto tempEmployeeDto = EmployeeDto.builder()
					.employeeId(employeeId)
					.employeePassword("111")
					.employeeName(employeeName)
					.employeeGender("남")
					.employeePhone(phone)
					.employeeEmail(email)
					.employeeDep("임시 부서")
					.employeePosition("임시 직급")
					.employeeBirth("21001231")
					.employeePostCode("12345")
					.employeeStreetAddress("도로명주소")
					.employeeDetailAddress("상세주소")
					.role(Role.EMPLOYEE) // 임시로 USER 역할 부여
					.build();

			EmployeeEntity tempEmployeeEntity = EmployeeEntity.toEmployeeEntityInsert(tempEmployeeDto, passwordEncoder);

			// Entity를 저장
			employeeRepository.save(tempEmployeeEntity);

			// 중복 체크를 위해 사용한 아이디, 휴대전화, 이메일을 목록에 추가
			existingIds.add(employeeId);
			existingPhones.add(phone);
			existingEmails.add(email);

			createdAccounts++;
			userNumber++;
		}
	}

	private String generateUniqueEmployeeId(List<String> existingIds) {
		String employeeId;
		Random rand = new Random();

		do {
			// 랜덤 아이디 생성
			int randomSuffix = rand.nextInt(100);
			employeeId = "user" + randomSuffix;
		} while (existingIds.contains(employeeId));

		return employeeId;
	}

	private String generateUniquePhone(List<String> existingPhones) {
		String phone;
		Random rand = new Random();

		do {
			// 랜덤 휴대전화 생성
			int randomSuffix = rand.nextInt(10000);
			phone = "010" + String.format("%04d", randomSuffix);
		} while (existingPhones.contains(phone));

		return phone;
	}

	private String generateUniqueEmail(List<String> existingEmails) {
		String email;
		Random rand = new Random();

		do {
			// 랜덤 이메일 생성
			int randomSuffix = rand.nextInt(10000);
			email = "user" + randomSuffix + "@example.com";
		} while (existingEmails.contains(email));

		return email;
	}


	@Test
	void employee() {
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

	@Test
	void employee2() {


	}



}
