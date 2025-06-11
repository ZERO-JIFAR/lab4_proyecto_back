package com.example.apirest.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CloudStorageService {

    // Directorio donde se guardarán las imágenes (ajusta según tu configuración)
    private final Path rootLocation = Paths.get("uploads");

    public CloudStorageService() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("No se pudo inicializar el almacenamiento", e);
        }
    }

    public String uploadFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("No se puede guardar un archivo vacío");
        }

        String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path destinationFile = this.rootLocation.resolve(filename);

        Files.copy(file.getInputStream(), destinationFile);

        // En un entorno real, aquí devolverías la URL completa de la imagen
        return "/uploads/" + filename;
    }

    public List<String> uploadFiles(List<MultipartFile> files) throws IOException {
        List<String> fileUrls = new ArrayList<>();

        for (MultipartFile file : files) {
            fileUrls.add(uploadFile(file));
        }

        return fileUrls;
    }
}
