package com.facturacion.ideas.api.exeption;

public class NotDataAccessException  extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	private static final String DESCRIPTION = "Not DataAccess Exception";

	public NotDataAccessException(String detail) {

		super(DESCRIPTION + ". " + detail);

	}

}
