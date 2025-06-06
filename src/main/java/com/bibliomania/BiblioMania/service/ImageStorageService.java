package com.bibliomania.BiblioMania.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;

@Service
public class ImageStorageService {

    private final Path storageLocation;

    public ImageStorageService(@Value("${app.upload.dir}") String uploadDir) throws IOException {
        this.storageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
        Files.createDirectories(this.storageLocation); // asegura que exista
    }

    public String storeImage(MultipartFile file, String filenameOverride) throws IOException {
        String filename = StringUtils.cleanPath(filenameOverride != null ? filenameOverride : file.getOriginalFilename());
        if (filename.contains("..")) {
            throw new IOException("Nombre de archivo no válido: " + filename);
        }

        Path targetPath = this.storageLocation.resolve(filename);
        Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
        return filename;
    }

    public Resource loadImage(String filename) throws MalformedURLException {
        Path filePath = this.storageLocation.resolve(filename).normalize();
        Resource resource = new UrlResource(filePath.toUri());

        if (resource.exists() && resource.isReadable()) {
            return resource;
        } else {
            throw new MalformedURLException("No se pudo cargar la imagen: " + filename);
        }
    }
}
