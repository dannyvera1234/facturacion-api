package com.facturacion.ideas.api.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IUploadFileService {

    String saveImage(MultipartFile file, String pathOutDirectory);
    
    void deleteImage(String nameFile);

}
