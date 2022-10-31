package com.facturacion.ideas.api.exeption;

public class ViolectRestrictException extends  RuntimeException{

    private static final long serialVersionUID = 1L;
    private static final String DESCRIPTION = "Violect Restrict";

    public ViolectRestrictException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }
}
