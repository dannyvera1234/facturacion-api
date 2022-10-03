package com.facturacion.ideas.api.dto;

import java.io.Serializable;

public class SubsidiaryNewDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long ide;

	private String code;

	private String socialReason;

	private String address;

	private boolean status;

	private String dateCreate;

	private boolean principal;

	public SubsidiaryNewDTO() {
		super();
	}

	public SubsidiaryNewDTO(Long ide, String code, String socialReason, String address, boolean status, String dateCreate,
			boolean principal) {
		super();
		this.ide = ide;
		this.code = code;
		this.socialReason = socialReason;
		this.address = address;
		this.status = status;
		this.dateCreate = dateCreate;
		this.principal = principal;
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

	public boolean isPrincipal() {
		return principal;
	}

	public void setPrincipal(boolean principal) {
		this.principal = principal;
	}

	@Override
	public String toString() {
		return "SubsidiaryNewDTO [ide=" + ide + ", code=" + code + ", socialReason=" + socialReason + ", address="
				+ address + ", status=" + status + ", dateCreate=" + dateCreate + ", principal=" + principal + "]";
	}

}
