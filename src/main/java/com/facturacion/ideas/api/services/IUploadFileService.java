package com.facturacion.ideas.api.services;

import com.facturacion.ideas.api.enums.TypeFileEnum;
import org.springframework.web.multipart.MultipartFile;


public interface IUploadFileService {

    String saveFile(MultipartFile file, String pathOutDirectory, TypeFileEnum typeFileEnum);

    void deleteImage(String nameFile);

}
