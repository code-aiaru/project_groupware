package spring.project.groupware.academy.employee.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import spring.project.groupware.academy.employee.entity.EmployeeEntity;
import spring.project.groupware.academy.employee.entity.ImageEntity;
import spring.project.groupware.academy.employee.repository.ImageRepository;
import spring.project.groupware.academy.util.FileStorageService;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final FileStorageService fileStorageService;
    private final ImageRepository imageRepository;

    public void saveEmployeeImage(EmployeeEntity employeeEntity,
                                  MultipartFile employeeImage) throws IOException {

        System.out.println(employeeImage);

        if (employeeImage != null && !employeeImage.isEmpty()) {
            String savedPath = fileStorageService.storeFile("employee", employeeImage);

            ImageEntity imageEntity = ImageEntity.builder()
                    .employee(employeeEntity)
                    .imageUrl(savedPath)
                    .build();

            imageRepository.save(imageEntity);
        }
    }

}
