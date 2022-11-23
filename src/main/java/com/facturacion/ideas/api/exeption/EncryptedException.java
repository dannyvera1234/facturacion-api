package com.facturacion.ideas.api.exeption;

public class EncryptedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private static final String DESCRIPTION = "EncryptedException";

    public EncryptedException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }
}
