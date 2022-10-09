package com.facturacion.ideas.api.dto;

import java.io.Serializable;

public class SubsidiaryResponseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String code;

	private String socialReason;

	private String address;

	private boolean status;

	private String dateCreate;


	public SubsidiaryResponseDTO() {
		super();

	}

	public SubsidiaryResponseDTO(String code, String socialReason, String address, boolean status, String dateCreate) {
		super();
		this.code = code;
		this.socialReason = socialReason;
		this.address = address;
		this.status = status;
		this.dateCreate = dateCreate;

	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSocialReason() {
		return socialReason;
	}

	public void setSocialReason(String socialReason) {
		this.socialReason = socialReason;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getDateCreate() {
		return dateCreate;
	}

	public void setDateCreate(String dateCreate) {
		this.dateCreate = dateCreate;
	}
	@Override
	public String toString() {
		return "SubsidiaryResponseDTO [code=" + code + ", socialReason=" + socialReason + ", address=" + address
				+ ", status=" + status + ", dateCreate=" + dateCreate + "]";
	}

}
