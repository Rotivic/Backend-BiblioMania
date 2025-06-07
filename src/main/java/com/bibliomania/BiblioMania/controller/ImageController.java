package com.bibliomania.BiblioMania.controller;

import com.bibliomania.BiblioMania.model.ApiError;
import com.bibliomania.BiblioMania.service.ImageStorageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    @Autowired
    private ImageStorageService imageStorageService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file,
                                              @RequestParam(value = "filename", required = false) String filenameOverride) {
        try {
            String filename = imageStorageService.storeImage(file, filenameOverride);
            return ResponseEntity.ok("Imagen subida con éxito: " + filename);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al subir la imagen");
        }
    }

    @GetMapping("/{filename:.+}")
    public ResponseEntity<?> getImage(@PathVariable String filename, HttpServletRequest request) {
        try {
            Resource file = imageStorageService.loadImage(filename);

            // Detectar tipo de contenido dinámicamente
            String contentType = null;
            try {
                contentType = request.getServletContext().getMimeType(file.getFile().getAbsolutePath());
            } catch (IOException ex) {
                // Fallback
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(file);

        } catch (IOException e) {
            ApiError error = new ApiError(
                    HttpStatus.NOT_FOUND.value(),
                    "No se pudo cargar la imagen: " + filename,
                    request.getRequestURI()
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
}
