package com.facturacion.ideas.api.exeption;

public class GenerateXMLExeption extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private static final String DESCRIPTION = "GenerateXML Exception";

    public GenerateXMLExeption(String detail) {
        super(DESCRIPTION + ". " + detail);
    }


}

