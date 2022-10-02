package com.facturacion.ideas.api.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.facturacion.ideas.api.enums.RolEnum;


@Entity
@Table(name = "cuentas")
public class Count implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CUE_COD")
	private Long ide;

	@Column(name = "CUE_RUC")
	private String ruc;

	@Column(name = "CUE_PAS")
	private String password;

	@Column(name = "CUE_EST")
	private boolean estado;

	@Column(name = "CUE_FEC_REG")
	private Date fechaRegistro;

	@Column(name = "CUE_ROL")
	@Enumerated(EnumType.STRING)
	private RolEnum rol;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "count")
	private List<Login> logins;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "count")
	//@JoinColumn(name = "CTP_FK_COD_CUE")
	private List<DetailsAggrement> detailsAggrement;

	@OneToMany(mappedBy = "count", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<Sender> sender;

	public Count(Long ide, String ruc, String password, boolean estado, Date fechaRegistro, RolEnum rol) {
		super();
		this.ide = ide;
		this.ruc = ruc;
		this.password = password;
		this.estado = estado;
		this.fechaRegistro = fechaRegistro;
		this.rol = rol;
		initData();
	}

	public Count() {
		super();
		initData();
	}

	private void initData() {
		logins = new ArrayList<>();
		detailsAggrement = new ArrayList<>();
		sender = new ArrayList<>();
	}

	@PrePersist
	private void prePersistData() {
		fechaRegistro = new Date();

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

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public RolEnum getRol() {
		return rol;
	}

	public void setRol(RolEnum rol) {
		this.rol = rol;
	}

	public List<Login> getLogins() {
		return logins;
	}

	public void addLogin(Login login) {
		this.logins.add(login);
	}

	public List<DetailsAggrement> getDetailsAggrement() {
		return detailsAggrement;
	}

	public void addDetailsAggrement(DetailsAggrement detailsAggrement) {
		this.detailsAggrement.add(detailsAggrement);
	}

	public List<Sender> getSender() {
		return sender;
	}

	public void setSender(Sender sender) {
		this.sender.add(sender);
	}

	@Override
	public String toString() {
		return "Count [ide=" + ide + ", ruc=" + ruc + ", password=" + password + ", estado=" + estado
				+ ", fechaRegistro=" + fechaRegistro + ", rol=" + rol + "]";
	}

}
