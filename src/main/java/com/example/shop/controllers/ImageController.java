package com.example.shop.controllers;

import com.example.shop.models.Image;
import com.example.shop.repositories.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequiredArgsConstructor
public class ImageController {

    private final ImageRepository imageRepository;

    //@Transactional
    @GetMapping("/images/{id}")
    private ResponseEntity<?> getImageById(@PathVariable Long id) {
        Image image = imageRepository.findById(id).orElse(null);

        if (image == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Image not found");
        }

        String encodedFileName = URLEncoder.encode(image.getOriginalFileName(), StandardCharsets.UTF_8);

        return ResponseEntity.ok()
                .header("fileName", encodedFileName)
                .contentType(MediaType.valueOf(image.getContentType()))
                .contentLength(image.getSize())
                .body(new InputStreamResource(new ByteArrayInputStream(image.getBytes())));
    }

}
