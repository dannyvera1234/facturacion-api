package com.facturacion.ideas.api.dto;

import java.io.Serializable;

public class CustomerResponseDTO extends PersonResponseDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String tlfConvencional;

	// private String extTlfConvencional;

	// private String typeCustomer;
	private String cellPhone;

	public CustomerResponseDTO() {
		super();
	}

	public CustomerResponseDTO(Long ide, String numberIdentification, String socialReason, String email, String address,
			String tlfConvencional, String cellPhone) {
		super(ide, numberIdentification, socialReason, email, address);
		this.tlfConvencional = tlfConvencional;
		this.cellPhone = cellPhone;
	}

	public String getTlfConvencional() {
		return tlfConvencional;
	}

	public void setTlfConvencional(String tlfConvencional) {
		this.tlfConvencional = tlfConvencional;
	}

	public String getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "CustomerResponseDTO [tlfConvencional=" + tlfConvencional + ", cellPhone=" + cellPhone + ", toString()="
				+ super.toString() + "]";
	}

}
