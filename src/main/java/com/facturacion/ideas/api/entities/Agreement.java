package com.facturacion.ideas.api.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import com.facturacion.ideas.api.enums.TypeAgreementEnum;

@Entity
@Table(name ="PLANES")
public class Agreement implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "PLA_COD")
	private String codigo;

	@Column(name = "PLA_DES")
	@Enumerated(value = EnumType.STRING)
	private TypeAgreementEnum typeAgreement;

	@Column(name = "PLA_VAL")
	private double value;

	public Agreement() {
		super();
	}

	public Agreement(String codigo, TypeAgreementEnum typeAgreement, double value) {
		super();
		this.codigo = codigo;
		this.typeAgreement = typeAgreement;
		this.value = value;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public TypeAgreementEnum getTypeAgreement() {
		return typeAgreement;
	}

	public void setTypeAgreement(TypeAgreementEnum typeAgreement) {
		this.typeAgreement = typeAgreement;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Agreement [codigo=" + codigo + ", typeAgreement=" + typeAgreement + ", value=" + value + "]";
	}

}
