package com.facturacion.ideas.api.dto;

import java.io.Serializable;

public abstract class DocumentResponseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long ide;

	private String typeDocument;

	private String numberSecuencial;

	private String keyAccess;

	private String numberAutorization;

	private String dateAutorization;

	private String dateEmission;

	private String typoEmision;

	public DocumentResponseDTO() {
		super();
	}

	public DocumentResponseDTO(Long ide, String typeDocument, String numberSecuencial, String keyAccess,
			String numberAutorization, String dateAutorization, String dateEmission, String typoEmision) {
		super();
		this.ide = ide;
		this.typeDocument = typeDocument;
		this.numberSecuencial = numberSecuencial;
		this.keyAccess = keyAccess;
		this.numberAutorization = numberAutorization;
		this.dateAutorization = dateAutorization;
		this.dateEmission = dateEmission;
		this.typoEmision = typoEmision;
	}

	public Long getIde() {
		return ide;
	}

	public void setIde(Long ide) {
		this.ide = ide;
	}

	public String getTypeDocument() {
		return typeDocument;
	}

	public void setTypeDocument(String typeDocument) {
		this.typeDocument = typeDocument;
	}

	public String getNumberSecuencial() {
		return numberSecuencial;
	}

	public void setNumberSecuencial(String numberSecuencial) {
		this.numberSecuencial = numberSecuencial;
	}

	public String getKeyAccess() {
		return keyAccess;
	}

	public void setKeyAccess(String keyAccess) {
		this.keyAccess = keyAccess;
	}

	public String getNumberAutorization() {
		return numberAutorization;
	}

	public void setNumberAutorization(String numberAutorization) {
		this.numberAutorization = numberAutorization;
	}

	public String getDateAutorization() {
		return dateAutorization;
	}

	public void setDateAutorization(String dateAutorization) {
		this.dateAutorization = dateAutorization;
	}

	public String getDateEmission() {
		return dateEmission;
	}

	public void setDateEmission(String dateEmission) {
		this.dateEmission = dateEmission;
	}

	public String getTypoEmision() {
		return typoEmision;
	}

	public void setTypoEmision(String typoEmision) {
		this.typoEmision = typoEmision;
	}

	@Override
	public String toString() {
		return "DocumentResponseDTO [ide=" + ide + ", typeDocument=" + typeDocument + ", numberSecuencial="
				+ numberSecuencial + ", keyAccess=" + keyAccess + ", numberAutorization=" + numberAutorization
				+ ", dateAutorization=" + dateAutorization + ", dateEmission=" + dateEmission + ", typoEmision="
				+ typoEmision + "]";
	}

}
