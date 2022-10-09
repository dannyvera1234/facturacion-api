package com.facturacion.ideas.api.dto;

import java.io.Serializable;

public class SenderResponseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String ruc;

	private String fullNameSocialReason;

	private String matrixAddress;

	private String specialContributor;

	private String accountancy;

	private String typeSender;

	// private String logo;

	private boolean rimpe;

	private String province;

	private String typeEnvironment;

	private String rol;

	public SenderResponseDTO() {
		super();
	}

	public SenderResponseDTO(String ruc, String fullNameSocialReason, String matrixAddress, String specialContributor,
			String accountancy, boolean rimpe, String province) {
		super();
		this.ruc = ruc;
		this.fullNameSocialReason = fullNameSocialReason;
		this.matrixAddress = matrixAddress;
		this.specialContributor = specialContributor;
		this.accountancy = accountancy;
		this.rimpe = rimpe;
		this.province = province;
	}

	public String getRuc() {
		return ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}

	public String getFullNameSocialReason() {
		return fullNameSocialReason;
	}

	public void setFullNameSocialReason(String fullNameSocialReason) {
		this.fullNameSocialReason = fullNameSocialReason;
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

	public String getAccountancy() {
		return accountancy;
	}

	public void setAccountancy(String accountancy) {
		this.accountancy = accountancy;
	}

	public boolean isRimpe() {
		return rimpe;
	}

	public void setRimpe(boolean rimpe) {
		this.rimpe = rimpe;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getTypeSender() {
		return typeSender;
	}

	public void setTypeSender(String typeSender) {
		this.typeSender = typeSender;
	}

	public String getTypeEnvironment() {
		return typeEnvironment;
	}

	public void setTypeEnvironment(String typeEnvironment) {
		this.typeEnvironment = typeEnvironment;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	@Override
	public String toString() {
		return "SenderResponseDTO [ruc=" + ruc + ", fullNameSocialReason=" + fullNameSocialReason + ", matrixAddress="
				+ matrixAddress + ", specialContributor=" + specialContributor + ", accountancy=" + accountancy
				+ ", rimpe=" + rimpe + ", province=" + province + "]";
	}

}
