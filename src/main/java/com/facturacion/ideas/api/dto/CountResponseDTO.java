package com.facturacion.ideas.api.dto;

import java.io.Serializable;

public class CountResponseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long ide;

	private String ruc;

	private boolean estado;

	private String fechaRegistro;

	private String rol;

	public CountResponseDTO() {
		super();
	}

	public CountResponseDTO(Long ide, String ruc, boolean estado, String fechaRegistro, String rol) {
		super();
		this.ide = ide;
		this.ruc = ruc;
		this.estado = estado;
		this.fechaRegistro = fechaRegistro;
		this.rol = rol;
	}

	public Long getIde() {
		return ide;
	}

	public void setIde(Long ide) {
		this.ide = ide;
	}

	public String getRuc() {
		return ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
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

}
