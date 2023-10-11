package spring.project.groupware.academy.employee.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import spring.project.groupware.academy.employee.dto.ImageResponseDto;
import spring.project.groupware.academy.employee.dto.ImageUploadDto;
import spring.project.groupware.academy.employee.entity.EmployeeEntity;
import spring.project.groupware.academy.employee.entity.ImageEntity;
import spring.project.groupware.academy.employee.repository.EmployeeRepository;
import spring.project.groupware.academy.employee.repository.ImageRepository;
import spring.project.groupware.academy.util.FileStorageService;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {

    private final FileStorageService fileStorageService;
    private final ImageRepository imageRepository;
    private final EmployeeRepository employeeRepository;

    // 이미지 URL 기본값 설정(이미지 없을 경우 기본 이미지로 설정)
    private final String defaultImageUrl = "/employeeImages/default.png";

    @Value("${file.employeeImgUploadDir}")
    private String uploadFolder;


    // 이미지 등록
//    public void saveEmployeeImage(EmployeeEntity employeeEntity,
//                                  MultipartFile employeeImage) throws IOException {
//
//        System.out.println(employeeImage);
//
//        employeeImage = new ImageUploadDto().getFile();
//        String imageFileName;
//
//        if (employeeImage.isEmpty()) {
//            // 파일이 업로드되지 않은 경우 기본 이미지 이름을 사용
//            imageFileName = "default.png";
//        } else {
//            UUID uuid = UUID.randomUUID();
//            imageFileName = uuid + "_" + employeeImage.getOriginalFilename();
//
//            File destinationFile = new File(uploadFolder + imageFileName);
//
//            try {
//                employeeImage.transferTo(destinationFile);
//            } catch (IOException e) {
//                log.info("이미지 업로드 중 오류 발생", e);
//                throw new RuntimeException("이미지 저장 실패", e);
//            }
//        }
//
//        ImageEntity imageEntity = imageRepository.findByEmployee(employeeEntity);
//
//        if (employeeImage != null && !employeeImage.isEmpty()) {
//            String savedPath = fileStorageService.storeFile("employee", employeeImage);
//
//            imageEntity = ImageEntity.builder()
//                    .employee(employeeEntity)
//                    .imageUrl(savedPath)
//                    .build();
//            log.info("이미지 업로드 및 저장 성공");
//        } else {
//            // 이미지가 이미 존재하면 URL 업데이트
//            imageEntity.updateUrl("/employeeImages/" + imageFileName);
//        }
//        imageRepository.save(imageEntity);
//
//        // 이미지 URL을 로그로 출력
//        log.info("이미지 URL: " + imageEntity.getImageUrl());
//    }
    public void upload(ImageUploadDto imageUploadDto, String employeeId) {
        EmployeeEntity employee = employeeRepository.findByEmployeeId(employeeId).orElseThrow(() ->
                new UsernameNotFoundException("아이디가 존재하지 않습니다"));

        MultipartFile file = imageUploadDto.getFile();
        String imageFileName;

        if (file.isEmpty()) {
            // 파일이 업로드되지 않은 경우 기본 이미지 이름을 사용
            imageFileName = "default.png";
        } else {
            UUID uuid = UUID.randomUUID();
            imageFileName = uuid + "_" + file.getOriginalFilename();

            File destinationFile = new File(uploadFolder + imageFileName);

            try {
                file.transferTo(destinationFile);
            } catch (IOException e) {
                log.info("이미지 업로드 중 오류 발생", e);
                throw new RuntimeException("이미지 저장 실패", e);
            }
        }

        ImageEntity image = imageRepository.findByEmployee(employee);
        if (image != null) {
            // 이미지가 이미 존재하면 URL 업데이트
            image.updateUrl("/employeeImages/" + imageFileName);
        } else {
            // 이미지가 없으면 객체 생성 후 저장
            image = ImageEntity.builder()
                    .employee(employee)
                    .imageUrl("/employeeImages/" + imageFileName)
                    .build();
            log.info("이미지 업로드 및 저장 성공");
        }
        imageRepository.save(image);

        // 이미지 URL을 로그로 출력
        log.info("이미지 URL: " + image.getImageUrl());
    }

    
    // 이미지 삭제
    public void deleteImage(String employeeId) {

        EmployeeEntity employee = employeeRepository.findByEmployeeId(employeeId).orElseThrow(() ->
                new UsernameNotFoundException("아이디가 존재하지 않습니다"));

        ImageEntity image = imageRepository.findByEmployee(employee);
        if (image != null) {
            // 이미지가 존재하면 삭제
            try {
                if (!image.getImageUrl().equals("/employeeImages/default.png")) {
                    // 기본 이미지가 아닌 경우에만 삭제
                    File imageFile = new File(uploadFolder + image.getImageUrl());
                    if (imageFile.exists()) {
                        imageFile.delete();
                    }
                }
            } catch (Exception e) {
                log.error("이미지 삭제 중 오류 발생", e);
                throw new RuntimeException("이미지 삭제 실패", e);
            }
            imageRepository.delete(image);
            log.info("이미지 삭제 성공");
        }
    }

    // 이미지 조회
    public ImageResponseDto findImage(String employeeId){
        EmployeeEntity employee = employeeRepository.findByEmployeeId(employeeId).orElseThrow(()->
                new UsernameNotFoundException("아이디가 존재하지않습니다"));
        ImageEntity image = imageRepository.findByEmployee(employee);

        String defaultImageUrl = "/employeeImages/default.png";

        if (image == null) {
            return ImageResponseDto.builder()
                    .imageUrl(defaultImageUrl)
                    .build();
        }else {
            return ImageResponseDto.builder()
                    .imageUrl(image.getImageUrl())
                    .build();
        }
    }

}
