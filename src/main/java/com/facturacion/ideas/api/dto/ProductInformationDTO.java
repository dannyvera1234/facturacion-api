package com.facturacion.ideas.api.dto;

import java.io.Serializable;

public class ProductInformationDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long ide;

	private String attribute;

	private String value;

	public ProductInformationDTO() {
		super();
	}

	public ProductInformationDTO(Long ide, String attribute, String value) {
		super();
		this.ide = ide;
		this.attribute = attribute;
		this.value = value;
	}

	public Long getIde() {
		return ide;
	}

	public void setIde(Long ide) {
		this.ide = ide;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "ProductInformationDTO [ide=" + ide + ", attribute=" + attribute + ", value=" + value + "]";
	}
	
	
	

}
