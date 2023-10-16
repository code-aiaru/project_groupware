//package spring.project.groupware.academy.home.config;
//
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import spring.project.groupware.academy.home.config.UrlRequestFilter;
//
//@Configuration
//public class UrlFilterConfig {
//
//    @Bean
//    public FilterRegistrationBean<UrlRequestFilter> loggingFilter() {
//        FilterRegistrationBean<UrlRequestFilter> registrationBean = new FilterRegistrationBean<>();
//        registrationBean.setFilter(new UrlRequestFilter());
//        registrationBean.addUrlPatterns("/dashboard");  // 여기에 URL 패턴 지정
//        registrationBean.setOrder(1);  // 필터의 우선 순위 지정
//
//        return registrationBean;
//    }
//}