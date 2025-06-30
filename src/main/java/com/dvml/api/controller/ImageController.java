package com.dvml.api.controller;

import com.dvml.api.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/api/images")
public class ImageController {


    @Autowired
    PessoaService pessoaService;
    private final String UPLOAD_DIR = "uploads/";

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile file, @RequestParam("pessoaId") long pessoaId  ) {
        try {
            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path path = Paths.get(UPLOAD_DIR + filename);
            Files.createDirectories(path.getParent());
            Files.write(path, file.getBytes());
            pessoaService.updatePhoto(filename, pessoaId);
            return ResponseEntity.ok(filename);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao salvar imagem");
        }
    }

    @GetMapping("/images/{filename}")
    public ResponseEntity<Resource> viewImage(@PathVariable String filename) throws IOException {
        Path path = Paths.get(UPLOAD_DIR).resolve(filename).normalize();
        Resource resource = new UrlResource(path.toUri());

        if (!resource.exists() || !resource.isReadable()) {
            return ResponseEntity.notFound().build();
        }

        String mimeType = Files.probeContentType(path);
        MediaType mediaType = MediaType.parseMediaType(mimeType);

        return ResponseEntity.ok()
                .contentType(mediaType)
                .body(resource);
    }
}
