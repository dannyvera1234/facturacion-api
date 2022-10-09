package com.facturacion.ideas.api.dto;

import java.io.Serializable;

public class EmployeeResponseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long ide;

	private String name;

	private String cedula;

	private String telephone;

	private String rol;

	private String subsidiary;

	public EmployeeResponseDTO() {
		super();
	}

	public EmployeeResponseDTO(Long ide, String name, String cedula, String telephone, String rol, String subsidiary) {
		super();
		this.ide = ide;
		this.name = name;
		this.cedula = cedula;
		this.telephone = telephone;
		this.rol = rol;
		this.subsidiary = subsidiary;
	}

	public Long getIde() {
		return ide;
	}

	public void setIde(Long ide) {
		this.ide = ide;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getSubsidiary() {
		return subsidiary;
	}

	public void setSubsidiary(String subsidiary) {
		this.subsidiary = subsidiary;
	}

	@Override
	public String toString() {
		return "EmployeeResponseDTO [ide=" + ide + ", name=" + name + ", cedula=" + cedula + ", telephone=" + telephone
				+ ", rol=" + rol + ", subsidiary=" + subsidiary + "]";
	}

}
