package com.facturacion.ideas.api.dto;

import java.io.Serializable;

public abstract class PersonResponseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long ide;

	// private String typeIdentification;

	private String numberIdentification;

	private String socialReason;

	private String email;

	public PersonResponseDTO() {
		super();
	}

	public PersonResponseDTO(Long ide, String numberIdentification, String socialReason, String email) {
		super();
		this.ide = ide;
		this.numberIdentification = numberIdentification;
		this.socialReason = socialReason;
		this.email = email;
	}

	public Long getIde() {
		return ide;
	}

	public void setIde(Long ide) {
		this.ide = ide;
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

	@Override
	public String toString() {
		return "PersonResponseDTO [ide=" + ide + ", numberIdentification=" + numberIdentification + ", socialReason="
				+ socialReason + ", email=" + email + "]";
	}

}
