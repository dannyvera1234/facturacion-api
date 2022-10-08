package com.facturacion.ideas.api.dto;

import java.io.Serializable;

public class CountNewDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long ide;

	private String ruc;

	private String password;

	private boolean estado;

	private String fechaRegistro;

	private String rol;

	private Long idAgreement;

	private int amount;

	public CountNewDTO() {
	}

	public CountNewDTO(Long ide, String ruc, String password, boolean estado, String fechaRegistro, String rol,
			Long idAgreement, int amount) {
		super();
		this.ide = ide;
		this.ruc = ruc;
		this.password = password;
		this.estado = estado;
		this.fechaRegistro = fechaRegistro;
		this.rol = rol;
		this.idAgreement = idAgreement;
		this.amount = amount;
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

	public void setIdAgreement(Long idAgreement) {
		this.idAgreement = idAgreement;
	}

	public Long getIdAgreement() {
		return idAgreement;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getAmount() {
		return amount;
	}

	@Override
	public String toString() {
		return "CountDTO [ide=" + ide + ", ruc=" + ruc + ", password=" + password + ", estado=" + estado
				+ ", fechaRegistro=" + fechaRegistro + ", rol=" + rol + "]";
	}

}
