package com.facturacion.ideas.api.exeption;

public class ConflictException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private static final String DESCRIPTION = "Conflict Exception";

    public ConflictException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }

}
