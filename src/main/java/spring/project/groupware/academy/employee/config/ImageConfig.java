package spring.project.groupware.academy.employee.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // 외부저장소(로컬) 접근 권한(허용할지 말지)
public class ImageConfig implements WebMvcConfigurer {

    @Value("${file.employeeImgUploadDir}")
    private String employeeImgUploadDir;

    @Value("${file.studentImgUploadDir}")
    private String studentImgUploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/employeeImages/**")
                .addResourceLocations("file:" + employeeImgUploadDir)
                // static/image에 들어있는 이미지들을 허가. // 상재씨 전 프로젝트 코드 참조
                .addResourceLocations("classpath:/static/images/employee/").setCachePeriod(60 * 60 * 24 * 365);



        registry.addResourceHandler("/studentImages/**")
                .addResourceLocations("file:" + studentImgUploadDir)
                // static/image에 들어있는 이미지들을 허가. // 상재씨 전 프로젝트 코드 참조
                .addResourceLocations("classpath:/static/images/student/").setCachePeriod(60 * 60 * 24 * 365);

    }
}
