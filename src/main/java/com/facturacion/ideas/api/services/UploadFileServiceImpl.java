package com.facturacion.ideas.api.services;

import com.facturacion.ideas.api.enums.TypeFileEnum;
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
    public String saveFile(MultipartFile file, String pathOutDirectory, TypeFileEnum typeFileEnum) {

        String fileNameOut = null;

        // Si selecciona un archivo
        if (!file.isEmpty()) {

            Path uploadPath = Paths.get(pathOutDirectory);

            // Verificar si existe el directorio
            if (!Files.exists(uploadPath)) {
                try {
                    Files.createDirectories(uploadPath);
                } catch (IOException e) {
                    LOGGER.error("No se pudo crear directorio: " + uploadPath + " para guardar" + typeFileEnum.getType(), e);
                    return null;
                }
            }

            if (typeFileEnum.getCode().equalsIgnoreCase(TypeFileEnum.IMG.getCode())) {
                fileNameOut = "logo_" + file.getOriginalFilename();
            } else if (typeFileEnum.getCode().equalsIgnoreCase(TypeFileEnum.FILE.getCode())) {
                fileNameOut = "certificado_" + file.getOriginalFilename();
            } else fileNameOut = "";

            try (InputStream inputStream = file.getInputStream()) {

                Path filePath = uploadPath.resolve(fileNameOut);

                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {

                LOGGER.error("Could not save " + typeFileEnum.getType() + uploadPath.toFile().getAbsolutePath() + "/" + fileNameOut, e);
                fileNameOut = null;
            }
        }

        return fileNameOut;
    }

    @Override
    public boolean deleteFile(String pathAndNameFile) {


        try {
            Path path = Paths.get(pathAndNameFile);
            return Files.deleteIfExists(path);
        } catch (Exception e) {
            LOGGER.error("Error al eliminar el archivo: " + pathAndNameFile, e);
            return false;
        }
    }


}
