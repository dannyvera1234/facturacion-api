package com.facturacion.ideas.api.dto;

import java.io.Serializable;

public class DriverNewDTO extends PersonNewDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String placa;

	public DriverNewDTO() {
		super();
	}

	public DriverNewDTO(Long ide, String typeIdentification, String numberIdentification, String socialReason,
			String email, String placa) {
		super(ide, typeIdentification, numberIdentification, socialReason, email);
		this.placa = placa;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	@Override
	public String toString() {
		return "DriverNewDTO [placa=" + placa + ", toString()=" + super.toString() + "]";
	}

}
