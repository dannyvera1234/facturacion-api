package com.facturacion.ideas.api.dto;

import java.io.Serializable;

public class ProductDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long ide;

	private String codePrivate;

	private String codeAuxilar;

	private String typeProductEnum;

	private String name;

	private double unitValue;
	
	private String dateCreate;

	private String iva;

	private String ice;

	private String irbpnr;

	private String sender;

	public ProductDTO() {
		super();
	}

	public ProductDTO(Long ide, String codePrivate, String codeAuxilar, String typeProductEnum, String name,
			double unitValue, String iva, String ice, String irbpnr, String sender) {
		super();
		this.ide = ide;
		this.codePrivate = codePrivate;
		this.codeAuxilar = codeAuxilar;
		this.typeProductEnum = typeProductEnum;
		this.name = name;
		this.unitValue = unitValue;
		this.iva = iva;
		this.ice = ice;
		this.irbpnr = irbpnr;
		this.sender = sender;
	}

	public Long getIde() {
		return ide;
	}

	public void setIde(Long ide) {
		this.ide = ide;
	}

	public String getCodePrivate() {
		return codePrivate;
	}

	public void setCodePrivate(String codePrivate) {
		this.codePrivate = codePrivate;
	}

	public String getCodeAuxilar() {
		return codeAuxilar;
	}

	public void setCodeAuxilar(String codeAuxilar) {
		this.codeAuxilar = codeAuxilar;
	}

	public String getTypeProductEnum() {
		return typeProductEnum;
	}

	public void setTypeProductEnum(String typeProductEnum) {
		this.typeProductEnum = typeProductEnum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getUnitValue() {
		return unitValue;
	}

	public void setUnitValue(double unitValue) {
		this.unitValue = unitValue;
	}

	public String getIva() {
		return iva;
	}

	public void setIva(String iva) {
		this.iva = iva;
	}

	public String getIce() {
		return ice;
	}

	public void setIce(String ice) {
		this.ice = ice;
	}

	public String getIrbpnr() {
		return irbpnr;
	}

	public void setIrbpnr(String irbpnr) {
		this.irbpnr = irbpnr;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}
	
	public String getDateCreate() {
		return dateCreate;
	}
	public void setDateCreate(String dateCreate) {
		this.dateCreate = dateCreate;
	}

	@Override
	public String toString() {
		return "ProductDTO [ide=" + ide + ", codePrivate=" + codePrivate + ", codeAuxilar=" + codeAuxilar
				+ ", typeProductEnum=" + typeProductEnum + ", name=" + name + ", unitValue=" + unitValue
				+ ", dateCreate=" + dateCreate + ", iva=" + iva + ", ice=" + ice + ", irbpnr=" + irbpnr + ", sender="
				+ sender + "]";
	}
	



}
