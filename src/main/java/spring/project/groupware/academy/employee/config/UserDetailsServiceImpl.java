package spring.project.groupware.academy.employee.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import spring.project.groupware.academy.employee.entity.EmployeeEntity;
import spring.project.groupware.academy.employee.repository.EmployeeRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private EmployeeRepository employeeRepository; // db에서 사용자 정보 조회 위해 사용
    
    @Autowired
    private PasswordEncoder passwordEncoder; // 비밀번호 인코딩 위해 사용

    // Spring Security에서 사용자 정보를 가져오는 역할
    @Override
    public UserDetails loadUserByUsername(String employeeId) throws UsernameNotFoundException {

        EmployeeEntity employeeEntity = employeeRepository.findByEmployeeId(employeeId).orElseThrow(()->{
            throw new UsernameNotFoundException("아이디가 없습니다");
        });

        return new MyUserDetails(employeeEntity);
    }
}
