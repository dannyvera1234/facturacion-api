package com.facturacion.ideas.api.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.facturacion.ideas.api.enums.TypeCustomerEnum;
import com.facturacion.ideas.api.enums.TypeIdentificationEnum;

@Entity
@Table(name = "CLIENTES")
@PrimaryKeyJoinColumn(referencedColumnName = "PER_COD", name = "CLI_PER")
public class Customer extends Person implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "CLI_TLF_CON")
	private String tlfConvencional;

	@Column(name = "CLI_EXT_TLF")
	private String extTlfConvencional;

	@Column(name = "CLI_CEL")
	private String cellPhone;

	@Column(name = "CLI_TIP")
	private String typeCustomer;

	public Customer() {
		super();
	}

	public Customer(Long ide, TypeIdentificationEnum typeIdentification, String numberIdentification,
			String socialReason, String email, String address, String tlfConvencional, String extTlfConvencional,
			String cellPhone, TypeCustomerEnum typeCustomer) {
		super(ide, typeIdentification, numberIdentification, socialReason, email, address);
		this.tlfConvencional = tlfConvencional;
		this.extTlfConvencional = extTlfConvencional;
		this.cellPhone = cellPhone;
		this.typeCustomer = typeCustomer.getCode();
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

	public void setTypeCustomer(TypeCustomerEnum typeCustomer) {
		this.typeCustomer = typeCustomer.getCode();
	}

	@Override
	public String toString() {
		return "Customer[tlfConvencional=" + tlfConvencional + ", extTlfConvencional=" + extTlfConvencional
				+ ", cellPhone=" + cellPhone + ", typeCustomer=" + typeCustomer + ", toString()=" + super.toString()
				+ "]";
	}

}
