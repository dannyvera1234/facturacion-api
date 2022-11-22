package com.facturacion.ideas.api.services;

import com.facturacion.ideas.api.sri.cliente.ClientSRI;
import com.facturacion.ideas.api.util.PathDocuments;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class UploadFileServiceImpl implements IUploadFileService {

    private static final Logger LOGGER = LogManager.getLogger(UploadFileServiceImpl.class);

    @Override
    public String saveImage(MultipartFile file, String pathOutDirectory) {

        String fileNameOut = "logo_default.jpg";

        // Si selecciona una imagen
        if (!file.isEmpty()) {

            Path uploadPath = Paths.get(pathOutDirectory);

            // Verificar si existe el directorio
            if (!Files.exists(uploadPath)) {
                try {
                    Files.createDirectories(uploadPath);
                } catch (IOException e) {
                    LOGGER.error("No se pudo crear directorio: " + uploadPath + " para el logo", e);

                    return fileNameOut;
                }
            }

            // Nombre de la imagen
            fileNameOut = "logo_" + file.getOriginalFilename();

            try (InputStream inputStream = file.getInputStream()) {

                Path filePath = uploadPath.resolve(fileNameOut);

                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {

                LOGGER.error("Could not save image: " + uploadPath.toFile().getAbsolutePath() + "/" + fileNameOut, e);
                // throw new IOException("Could not save image file: " + file, ioe);
                fileNameOut = "logo_default.jpg";
            }

        }
        return fileNameOut;
    }

    @Override
    public void deleteImage(String nameFile) {

    }
}
