package com.facturacion.ideas.api.dto;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class CustomerNewDTO extends PersonNewDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String tlfConvencional;

	private String extTlfConvencional;

	private String cellPhone;

	@NotBlank(message = "El tipo de cliente no pueder vacío")
	private String typeCustomer;

	public CustomerNewDTO() {
		super();
	}

	public CustomerNewDTO(Long ide, String typeIdentification, String numberIdentification, String socialReason,
			String email, String address, String tlfConvencional, String extTlfConvencional, String cellPhone,
			String typeCustomer) {
		super(ide, typeIdentification, numberIdentification, socialReason, email, address);
		this.tlfConvencional = tlfConvencional;
		this.extTlfConvencional = extTlfConvencional;
		this.cellPhone = cellPhone;
		this.typeCustomer = typeCustomer;
	}

	public String getTlfConvencional() {
		return tlfConvencional;
	}

	public void setTlfConvencional(String tlfConvencional) {
		this.tlfConvencional = tlfConvencional;
	}

	public String getExtTlfConvencional() {
		return extTlfConvencional;
	}

	public void setExtTlfConvencional(String extTlfConvencional) {
		this.extTlfConvencional = extTlfConvencional;
	}

	public String getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	public String getTypeCustomer() {
		return typeCustomer;
	}

	public void setTypeCustomer(String typeCustomer) {
		this.typeCustomer = typeCustomer;
	}

	@Override
	public String toString() {
		return "CustomerNewDTO [tlfConvencional=" + tlfConvencional + ", extTlfConvencional=" + extTlfConvencional
				+ ", cellPhone=" + cellPhone + ", typeCustomer=" + typeCustomer + ", toString()=" + super.toString()
				+ "]";
	}

}
