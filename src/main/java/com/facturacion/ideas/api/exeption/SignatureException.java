package com.facturacion.ideas.api.exeption;

public class SignatureException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private static final String DESCRIPTION = "Signature Exception";

    public SignatureException(String detail) {

        super(DESCRIPTION + ". " + detail);

    }
}
