package com.facturacion.ideas.api.services;

public interface IEncryptionService {

    String encrypt(String textPlane);

    String deEncrypt(String textEncrypt);

}
