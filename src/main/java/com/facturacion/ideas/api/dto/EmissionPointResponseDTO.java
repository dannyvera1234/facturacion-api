package com.facturacion.ideas.api.dto;

import java.io.Serializable;

public class EmissionPointResponseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long ide;

	private String codePoint;

	private boolean status;

	private String dateRegister;

	private String keyPoint;

	private String fullNameEmployee;

	public EmissionPointResponseDTO() {
		super();
	}

	public EmissionPointResponseDTO(Long ide, String codePoint, boolean status, String dateRegister, String keyPoint,
			String fullNameEmployee) {
		super();
		this.ide = ide;
		this.codePoint = codePoint;
		this.status = status;
		this.dateRegister = dateRegister;
		this.keyPoint = keyPoint;
		this.fullNameEmployee = fullNameEmployee;
	}

	public Long getIde() {
		return ide;
	}

	public void setIde(Long ide) {
		this.ide = ide;
	}

	public String getCodePoint() {
		return codePoint;
	}

	public void setCodePoint(String codePoint) {
		this.codePoint = codePoint;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getDateRegister() {
		return dateRegister;
	}

	public void setDateRegister(String dateRegister) {
		this.dateRegister = dateRegister;
	}

	public String getKeyPoint() {
		return keyPoint;
	}

	public void setKeyPoint(String keyPoint) {
		this.keyPoint = keyPoint;
	}

	public String getFullNameEmployee() {
		return fullNameEmployee;
	}

	public void setFullNameEmployee(String fullNameEmployee) {
		this.fullNameEmployee = fullNameEmployee;
	}

	@Override
	public String toString() {
		return "EmissionPointResponseDTO [ide=" + ide + ", codePoint=" + codePoint + ", status=" + status
				+ ", dateRegister=" + dateRegister + ", keyPoint=" + keyPoint + ", fullNameEmployee=" + fullNameEmployee
				+ "]";
	}

}
