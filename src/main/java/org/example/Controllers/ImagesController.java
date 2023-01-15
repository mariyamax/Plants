package org.example.Controllers;

import lombok.SneakyThrows;
import org.example.Models.Images;
import org.example.Repos.ImagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Files;

@RestController
public class ImagesController {

    @Autowired
    private ImagesRepository imageRepository;

    @GetMapping("api/images/{id}")
    private ResponseEntity<?> getImageById(@PathVariable Long id) {
        Images image = imageRepository.findByID(id);
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(image.getFileExtension()))
                .contentLength(image.getSize())
                .body(new InputStreamResource(new ByteArrayInputStream(image.getBytes())));
    }

    @SneakyThrows
    @GetMapping("api/images/project")
    private ResponseEntity<?> getDefaultProjectImage() {
        File image = new File("src/main/resources/images/Project.png");
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .contentLength(image.length())
                .body(new InputStreamResource(new ByteArrayInputStream(Files.readAllBytes(image.toPath()))));
    }

    @SneakyThrows
    @GetMapping("api/images/user/{id}")
    private ResponseEntity<?> getDefaultUserImage(@PathVariable Long id) {
        File image;
        if(id%2==0){
            image = new File("src/main/resources/images/girl.png");
        } else {
            image = new File("src/main/resources/images/boy.png");
        }
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .contentLength(image.length())
                .body(new InputStreamResource(new ByteArrayInputStream(Files.readAllBytes(image.toPath()))));
    }

    @SneakyThrows
    @GetMapping("api/images/alert")
    private ResponseEntity<?> getProjectAlertImage() {
        File image = new File("src/main/resources/images/image.png");
        System.out.println(image.exists());
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .contentLength(image.length())
                .body(new InputStreamResource(new ByteArrayInputStream(Files.readAllBytes(image.toPath()))));
    }

    @SneakyThrows
    @GetMapping("api/images/chat")
    private ResponseEntity<?> getDefaultChatImage() {
        File image = new File("src/main/resources/images/chat.png");
        System.out.println(image.exists());
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .contentLength(image.length())
                .body(new InputStreamResource(new ByteArrayInputStream(Files.readAllBytes(image.toPath()))));
    }

}
