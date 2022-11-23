package com.facturacion.ideas.api.services;

import org.springframework.web.multipart.MultipartFile;


public interface IUploadFileService {

    String saveImage(MultipartFile file, String pathOutDirectory);
    
    void deleteImage(String nameFile);

}
