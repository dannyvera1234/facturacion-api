package com.facturacion.ideas.api.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.facturacion.ideas.api.enums.RolEnum;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "empleados")
public class Employee implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "EMP_COD")
	private Long ide;

	@Column(name = "EMP_NOM")
	private String name;

	@Column(name = "EMP_CED")
	private String cedula;

	@Column(name = "EMP_ROL")
	@Enumerated(EnumType.STRING)
	private RolEnum rol;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EMP_FK_COD_EST")
	@JsonBackReference
	private Subsidiary subsidiary;

	public Employee() {

	}

	public Employee(Long ide, String name, String cedula, RolEnum rol) {
		super();
		this.ide = ide;
		this.name = name;
		this.cedula = cedula;
		this.rol = rol;

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

	public RolEnum getRol() {
		return rol;
	}

	public void setRol(RolEnum rol) {
		this.rol = rol;
	}

	public Subsidiary getSubsidiary() {
		return subsidiary;
	}

	public void setSubsidiary(Subsidiary subsidiary) {
		this.subsidiary = subsidiary;
	}

	@Override
	public String toString() {
		return "Employee [ide=" + ide + ", name=" + name + ", cedula=" + cedula + ", rol=" + rol + ", subsidiary="
				+ subsidiary.getCode() + "]";
	}

}
