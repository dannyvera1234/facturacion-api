package com.facturacion.ideas.api.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class EmissionPointNewDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long ide;

	@NotBlank(message = "El código del punto emisión no puede ser vacío")
	@Size(min = 3, max = 3, message = "El código del punto emisión debe tener 3 dígitos")
	private String codePoint;

	private boolean status;

	private String dateRegister;

	@NotBlank(message = "La clave del punto emisión no puede ser vacío")
	private String keyPoint;

	private Long idEmployee;

	public EmissionPointNewDTO() {
		super();
	}

	public EmissionPointNewDTO(Long ide, String codePoint, boolean status, String dateRegister, String keyPoint,
			Long idEmployee) {
		super();
		this.ide = ide;
		this.codePoint = codePoint;
		this.status = status;
		this.dateRegister = dateRegister;
		this.keyPoint = keyPoint;
		this.idEmployee = idEmployee;
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

	public void setIdEmployee(Long idEmployee) {
		this.idEmployee = idEmployee;
	}

	public Long getIdEmployee() {
		return idEmployee;
	}

	@Override
	public String toString() {
		return "EmissionPointNewDTO [ide=" + ide + ", codePoint=" + codePoint + ", status=" + status + ", dateRegister="
				+ dateRegister + ", keyPoint=" + keyPoint + ", idEmployee=" + idEmployee + "]";
	}



}
