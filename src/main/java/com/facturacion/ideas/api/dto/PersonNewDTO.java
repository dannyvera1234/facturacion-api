package com.facturacion.ideas.api.dto;

import java.io.Serializable;

public abstract class PersonNewDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long ide;

	private String typeIdentification;

	private String numberIdentification;

	private String socialReason;

	private String email;

	private String address;

	public PersonNewDTO() {
		super();
	}

	public PersonNewDTO(Long ide, String typeIdentification, String numberIdentification, String socialReason,
			String email, String address) {
		super();
		this.ide = ide;
		this.typeIdentification = typeIdentification;
		this.numberIdentification = numberIdentification;
		this.socialReason = socialReason;
		this.email = email;
		this.address = address;
	}

	public Long getIde() {
		return ide;
	}

	public void setIde(Long ide) {
		this.ide = ide;
	}

	public String getTypeIdentification() {
		return typeIdentification;
	}

	public void setTypeIdentification(String typeIdentification) {
		this.typeIdentification = typeIdentification;
	}

	public String getNumberIdentification() {
		return numberIdentification;
	}

	public void setNumberIdentification(String numberIdentification) {
		this.numberIdentification = numberIdentification;
	}

	public String getSocialReason() {
		return socialReason;
	}

	public void setSocialReason(String socialReason) {
		this.socialReason = socialReason;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "PersonNewDTO [ide=" + ide + ", typeIdentification=" + typeIdentification + ", numberIdentification="
				+ numberIdentification + ", socialReason=" + socialReason + ", email=" + email + ", address=" + address
				+ "]";
	}

}
