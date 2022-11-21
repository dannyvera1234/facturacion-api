package com.facturacion.ideas.api.exeption;

public class ConnectionWSException extends RuntimeException {


    private static final long serialVersionUID = 1L;

    private static final String DESCRIPTION = "ConnectionWSException Exception";

    public ConnectionWSException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }

}
