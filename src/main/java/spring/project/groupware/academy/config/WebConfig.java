//package spring.project.groupware.academy.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//
//        registry.addResourceHandler("/dist/**")
//                // static/image에 들어있는 이미지들을 허가.
//                .addResourceLocations("classpath:/fullcalendar-6.1.9/dist/")
//                .setCachePeriod(60 * 60 * 24 * 365);
//    }
//}
