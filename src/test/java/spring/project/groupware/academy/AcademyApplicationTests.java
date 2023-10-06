package spring.project.groupware.academy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import spring.project.groupware.academy.employee.constraint.Role;
import spring.project.groupware.academy.employee.dto.EmployeeDto;
import spring.project.groupware.academy.employee.entity.EmployeeEntity;
import spring.project.groupware.academy.employee.repository.EmployeeRepository;

@SpringBootTest
class AcademyApplicationTests {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Test
	void contextLoads() {

		// 임시계정 생성(admin)
		EmployeeDto tempEmployeeDto = EmployeeDto.builder()
				.employeeId("admin")
				.employeePassword("1111")
				.employeeName("관리자")
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

		// 임시계정을 Entity로 변환
		EmployeeEntity tempEmployeeEntity = EmployeeEntity.toEmployeeEntityInsert(tempEmployeeDto, passwordEncoder);

		// Entity를 저장
		employeeRepository.save(tempEmployeeEntity);
	}



}
