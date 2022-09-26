package com.facturacion.ideas.api.exeption;

public class DuplicatedResourceException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	private static final String DESCRIPTION = "Duplicated Resource Exception";

	public DuplicatedResourceException(String detail) {

		super(DESCRIPTION + ". " + detail);

	}

}
