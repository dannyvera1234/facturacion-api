package com.facturacion.ideas.api.dto;

import java.io.Serializable;

public class EmployeeDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long ide;

	private String name;

	private String cedula;
	
	private String telephone;

	private String rol;

	private String subsidiary;

	public EmployeeDTO(Long ide, String name, String cedula, String rol, String telephone, String subsidiary) {

		this.ide = ide;
		this.name = name;
		this.cedula = cedula;
		this.rol = rol;
		this.telephone = telephone;
		this.subsidiary = subsidiary;
	}

	public EmployeeDTO() {
		super();
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

	public String getTelephone() {
		return telephone;
	}
	
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@Override
	public String toString() {
		return "EmployeeDTO [ide=" + ide + ", name=" + name + ", cedula=" + cedula + ", telephone=" + telephone
				+ ", rol=" + rol + ", subsidiary=" + subsidiary + "]";
	}
	
	

}
