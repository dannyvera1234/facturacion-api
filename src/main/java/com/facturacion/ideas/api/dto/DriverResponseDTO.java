package com.facturacion.ideas.api.dto;

import java.io.Serializable;

public class DriverResponseDTO extends PersonResponseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String placa;

	public DriverResponseDTO() {
		super();
	}

	public DriverResponseDTO(Long ide, String typeIdentification, String numberIdentification, String socialReason,
			String email, String address, String placa) {
		super(ide, typeIdentification, numberIdentification, socialReason, email, address);
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
		return "DriverResponseDTO [placa=" + placa + ", toString()=" + super.toString() + "]";
	}

}
