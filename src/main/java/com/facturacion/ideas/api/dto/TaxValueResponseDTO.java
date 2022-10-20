package com.facturacion.ideas.api.dto;

import java.io.Serializable;

public class TaxValueResponseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	// Este el autincrementable, no tiene mucha in¿mpirtancia
	private Long ide;

	// este el codigo de cada impuesto valor
	private String code;

	//private double porcentage;

	//private double retentionPorcentage;

	//private String typeTax;

	private String description;

	//private String srtartDate;

	//private String endDate;

	//private int codAdmin;

	//private String porcentageMarkFree;

	//private String tax;

	
	
	public TaxValueResponseDTO() {
		super();
	}

	public TaxValueResponseDTO(Long ide, String code, String description) {
		super();
		this.ide = ide;
		this.code = code;
		this.description = description;
	}

	public Long getIde() {
		return ide;
	}

	public void setIde(Long ide) {
		this.ide = ide;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "TaxValueDTO [ide=" + ide + ", code=" + code + ", description=" + description + "]";
	}




	
	
	

}
