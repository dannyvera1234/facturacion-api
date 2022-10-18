package com.facturacion.ideas.api.exeption;

public class KeyAccessException extends RuntimeException {

	private static final long serialVersionUID = 1L;


	private static final String DESCRIPTION = "KeyAccessException";

	public KeyAccessException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }

}
