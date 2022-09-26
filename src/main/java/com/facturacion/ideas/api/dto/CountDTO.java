package com.facturacion.ideas.api.dto;

import java.io.Serializable;

public class CountDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int ide;

	private String ruc;

	private String password;

	private boolean estado;

	private String fechaRegistro;

	private String rol;

	public CountDTO(int ide, String ruc, String password, boolean estado, String fechaRegistro, String rol) {
		super();
		this.ide = ide;
		this.ruc = ruc;
		this.password = password;
		this.estado = estado;
		this.fechaRegistro = fechaRegistro;
		this.rol = rol;
	}

	public int getIde() {
		return ide;
	}

	public void setIde(int ide) {
		this.ide = ide;
	}

	public String getRuc() {
		return ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public String getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(String fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	@Override
	public String toString() {
		return "CountDTO [ide=" + ide + ", ruc=" + ruc + ", password=" + password + ", estado=" + estado
				+ ", fechaRegistro=" + fechaRegistro + ", rol=" + rol + "]";
	}

}
