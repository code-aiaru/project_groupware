package spring.project.groupware.academy.employee.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {


    // 일반 사원
    @Configuration
    public static class EmployeeConfig {

        @Autowired
        private DataSource dataSource;

        @Bean
        public SecurityFilterChain filterChainApp1(HttpSecurity http) throws Exception {
                    http.csrf().disable();
            http.authorizeRequests()
                    .antMatchers("/login","/movie/saveBoxOfficeData").permitAll()
                    .antMatchers("/", "/dashboard", "/logout", "/employee/simple**", "/employee/detail/**", "/employee/update/**", "/employee/updateImage/**",
                            "/employee/delete/**", "/employee/confirmPassword/**", "/employee/changePassword/**", "/student/**", "/boards/**", "/notice/detail/**",
                            "/notice/list", "/attendance/**", "/approval/**", "/naver/**" ,"/post/**", "/weather**", "/movie", "/bus").authenticated()
                    .antMatchers("/employee/join", "/employee/employeeList**", "/notice/create", "/notice/edit/**").hasAnyRole("ADMIN")

                    .and()
                    .formLogin()
                    .loginPage("/login")
                    .usernameParameter("employeeId")
                    .passwordParameter("employeePassword")
                    .loginProcessingUrl("/employee/login/post")
                    .failureUrl("/login")
                    .defaultSuccessUrl("/");

            // 자동로그인 기능
            http.rememberMe()
                    .rememberMeParameter("rememberMe")
                    .tokenValiditySeconds(86400 * 7) // 7일
                    .alwaysRemember(false) // true 시 무조건 자동로그인, 기본값은 false
                    .userDetailsService(userDetailsService());

            http.logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/")

                    .and()
                    .authenticationProvider(userAuthenticationProvider()); // 사용자 지정 로직을 통해 사용자를 인증하고 Spring Security에게 사용자 정보를 제공하는 역할
            return http.build();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Bean
        public UserDetailsServiceImpl userDetailsService() {
            return new UserDetailsServiceImpl();
        }

        @Bean
        public DaoAuthenticationProvider userAuthenticationProvider() {
            DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
            provider.setUserDetailsService(userDetailsService());
            provider.setPasswordEncoder(passwordEncoder());
            return provider;
        }


        @Bean
        public RestTemplate restTemplate() {
            return new RestTemplate();
        }

    }


}
