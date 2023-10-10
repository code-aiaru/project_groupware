package spring.project.groupware.academy.employee.dto;


import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ImageUploadDto {

    private MultipartFile file;
}
