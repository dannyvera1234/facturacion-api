package com.facturacion.ideas.api.exeption;


import java.util.Map;

class ErrorMessage {

    private final String error;
    private final Object message;
    private final Integer code;

	private final boolean validation;

    ErrorMessage(Exception exception, Integer code) {
        this.error = exception.getClass().getSimpleName();
        this.message = exception.getMessage();
        this.code = code;
		this.validation = false;
    }
	ErrorMessage(Exception exception, Map<String, String> data, Integer code) {
		this.error = exception.getClass().getSimpleName();
		this.message = data;
		this.code = code;
		validation = true;
	}



	public String getError() {
		return error;
	}

	public Object getMessage() {
		return message;
	}

	public Integer getCode() {
		return code;
	}

	public boolean isValidation() {
		return validation;
	}

	@Override
	public String toString() {
		return "ErrorMessage [error=" + error + ", message=" + message + ", code=" + code + "]";
	}
 
    
}
