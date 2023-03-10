package com.facturacion.ideas.api.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProductDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long ide;

	@NotBlank(message = "El código privado del producto no puede ser vacío")
	@Size(min =1, max = 24,  message = "EL código privado del producto debe tener entre 1 y 24 dígitos")
	private String codePrivate;

	private String codeAuxilar;

	@NotBlank(message = "El tipo de producto no puede ser vacío")
	@Size(min =1, max = 1,  message = "El código del tipo de producto debe tener 1 dígito")
	private String typeProductEnum;

	@NotBlank(message = "El nombre/descripción del producto no puede ser vacío")
	@Size(min =1, max = 95,  message = "El nombre/descripción  del producto debe tener entre 1 y 95 dígitos")
	private String name;

	@PositiveOrZero(message = "El precio unitario del producto no pueder ser negativo")
	private double unitValue;

	private String dateCreate;

	@NotBlank(message = "El iva del producto no puede ser vacío")
	@Size(min =1, max = 1,  message = "El código de iva del producto debe tener 1 dígito")
	private String iva;

	private String ice;

	private String irbpnr;

	private String sender;

	private List<ProductInformationDTO> productInformationDTOs;

	public ProductDTO() {
		super();
		initData();
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

		initData();
	}

	private void initData() {

		productInformationDTOs = new ArrayList<>();
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

	public List<ProductInformationDTO> getProductInformationDTOs() {
		return productInformationDTOs;
	}

	public void addProductInformationDTO(ProductInformationDTO productInformationDTO) {

		productInformationDTOs.add(productInformationDTO);
	}

	@Override
	public String toString() {
		return "ProductDTO [ide=" + ide + ", codePrivate=" + codePrivate + ", codeAuxilar=" + codeAuxilar
				+ ", typeProductEnum=" + typeProductEnum + ", name=" + name + ", unitValue=" + unitValue
				+ ", dateCreate=" + dateCreate + ", iva=" + iva + ", ice=" + ice + ", irbpnr=" + irbpnr + ", sender="
				+ sender + "]";
	}

}
