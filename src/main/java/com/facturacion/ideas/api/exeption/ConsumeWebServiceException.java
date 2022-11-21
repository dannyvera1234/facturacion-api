package com.facturacion.ideas.api.exeption;

public class ConsumeWebServiceException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private static final String DESCRIPTION = "ConsumeWebServiceException Exception";

    public ConsumeWebServiceException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }
}
