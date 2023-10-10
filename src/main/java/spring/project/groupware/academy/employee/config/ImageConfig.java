package spring.project.groupware.academy.employee.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // 외부저장소(로컬) 접근 권한(허용할지 말지)
public class ImageConfig implements WebMvcConfigurer {

    @Value("${file.employeeImgUploadDir}")
    private String employeeImgUploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/employeeImages/**")
                .addResourceLocations("file:" + employeeImgUploadDir);
    }
}
