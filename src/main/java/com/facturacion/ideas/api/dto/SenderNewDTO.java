package com.facturacion.ideas.api.dto;

import java.io.Serializable;

import com.facturacion.ideas.api.enums.ProvinceEnum;
import com.facturacion.ideas.api.enums.QuestionEnum;
import com.facturacion.ideas.api.enums.TypeEmissionEnum;
import com.facturacion.ideas.api.enums.TypeEnvironmentEnum;
import com.facturacion.ideas.api.enums.TypeSenderEnum;

public class SenderNewDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long ide;

	private String ruc;

	private String socialReason;

	private String commercialName;

	private String matrixAddress;

	private String specialContributor;

	private QuestionEnum accountancy;
	
	private TypeSenderEnum typeSender;
	
	private String logo;
	
	private TypeEnvironmentEnum typeEnvironment;

	private TypeEmissionEnum typeEmission;

	private boolean rimpe;
	
	private ProvinceEnum province;
	

	public SenderNewDTO(Long ide, String ruc, String socialReason, String commercialName, String matrixAddress,
			String specialContributor, QuestionEnum accountancy, TypeSenderEnum typeSender, String logo,
			TypeEnvironmentEnum typeEnvironment, TypeEmissionEnum typeEmission, boolean rimpe, ProvinceEnum province) {
		super();
		this.ide = ide;
		this.ruc = ruc;
		this.socialReason = socialReason;
		this.commercialName = commercialName;
		this.matrixAddress = matrixAddress;
		this.specialContributor = specialContributor;
		this.accountancy = accountancy;
		this.typeSender = typeSender;
		this.logo = logo;
		this.typeEnvironment = typeEnvironment;
		this.typeEmission = typeEmission;
		this.rimpe = rimpe;
		this.province = province;
	}

	public Long getIde() {
		return ide;
	}

	public void setIde(Long ide) {
		this.ide = ide;
	}

	public String getRuc() {
		return ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}

	public String getSocialReason() {
		return socialReason;
	}

	public void setSocialReason(String socialReason) {
		this.socialReason = socialReason;
	}

	public String getCommercialName() {
		return commercialName;
	}

	public void setCommercialName(String commercialName) {
		this.commercialName = commercialName;
	}

	public String getMatrixAddress() {
		return matrixAddress;
	}

	public void setMatrixAddress(String matrixAddress) {
		this.matrixAddress = matrixAddress;
	}

	public String getSpecialContributor() {
		return specialContributor;
	}

	public void setSpecialContributor(String specialContributor) {
		this.specialContributor = specialContributor;
	}

	public QuestionEnum getAccountancy() {
		return accountancy;
	}

	public void setAccountancy(QuestionEnum accountancy) {
		this.accountancy = accountancy;
	}

	public TypeSenderEnum getTypeSender() {
		return typeSender;
	}

	public void setTypeSender(TypeSenderEnum typeSender) {
		this.typeSender = typeSender;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public TypeEnvironmentEnum getTypeEnvironment() {
		return typeEnvironment;
	}

	public void setTypeEnvironment(TypeEnvironmentEnum typeEnvironment) {
		this.typeEnvironment = typeEnvironment;
	}

	public TypeEmissionEnum getTypeEmission() {
		return typeEmission;
	}

	public void setTypeEmission(TypeEmissionEnum typeEmission) {
		this.typeEmission = typeEmission;
	}

	public boolean isRimpe() {
		return rimpe;
	}

	public void setRimpe(boolean rimpe) {
		this.rimpe = rimpe;
	}

	public ProvinceEnum getProvince() {
		return province;
	}

	public void setProvince(ProvinceEnum province) {
		this.province = province;
	}

	@Override
	public String toString() {
		return "SenderNewDTO [ide=" + ide + ", ruc=" + ruc + ", socialReason=" + socialReason + ", commercialName="
				+ commercialName + ", matrixAddress=" + matrixAddress + ", specialContributor=" + specialContributor
				+ ", accountancy=" + accountancy + ", typeSender=" + typeSender + ", logo=" + logo
				+ ", typeEnvironment=" + typeEnvironment.getCode() + ", typeEmission=" + typeEmission.getCode() + ", rimpe=" + rimpe
				+ ", province=" + province.getName() + "]";
	}
	
	
	

}
