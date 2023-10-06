package spring.project.groupware.academy.employee.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {


    // 일반 사원
    @Configuration
    public static class EmployeeConfig{

        @Bean
        public SecurityFilterChain filterChainApp1(HttpSecurity http) throws Exception {
            http.authorizeHttpRequests()
                    .antMatchers("/", "/employee/join", "/login").permitAll() // "/employee/join" 테스트용으로 둔것, 삭제해야함
                    .antMatchers("/logout", "/employee/detail/**", "/employee/update/**", "/employee/updateImage/**", "/employee/delete/**",
                            "/board/**", "/employee/inquiry**", "/student/join", "/inquiry/**").authenticated()
                    .antMatchers("/employee/join", "/employee/manage**").hasAnyRole("ADMIN")

                    .and()
                    .formLogin()
                    .loginPage("/login")
                    .usernameParameter("employeeId")
                    .passwordParameter("employeePassword")
                    .loginProcessingUrl("/employee/login/post")
                    .failureUrl("/login")
                    .defaultSuccessUrl("/");

//                    .and()
//                    .oauth2Login()
//                    .loginPage("/login")
//                    .userInfoEndpoint()
//                    .userService(myAuth2UserService());

            http.logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/")

                    .and()
                    .csrf().disable()
                    .authenticationProvider(userAuthenticationProvider()); // 사용자 지정 로직을 통해 사용자를 인증하고 Spring Security에게 사용자 정보를 제공하는 역할
            return http.build();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

//        @Bean
//        public OAuth2UserService<OAuth2UserRequest, OAuth2User> myAuth2UserService() {
//            return new MyOAuth2UserService();
//        }

        @Bean
        public UserDetailsServiceImpl userDetailsService(){
            return new UserDetailsServiceImpl();
        }

        @Bean
        public DaoAuthenticationProvider userAuthenticationProvider(){
            DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
            provider.setUserDetailsService(userDetailsService());
            provider.setPasswordEncoder(passwordEncoder());
            return provider;
        }
    }


}
