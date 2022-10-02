package com.facturacion.ideas.api.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.facturacion.ideas.api.enums.TypeAgreementEnum;

@Entity
@Table(name ="PLANES")
public class Agreement implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "PLA_COD")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;

	@Column(name = "PLA_DES")
	@Enumerated(value = EnumType.STRING)
	private TypeAgreementEnum typeAgreement;

	@Column(name = "PLA_VAL")
	private double value;

	public Agreement() {
		super();
	}

	public Agreement(Long codigo, TypeAgreementEnum typeAgreement, double value) {
		super();
		this.codigo = codigo;
		this.typeAgreement = typeAgreement;
		this.value = value;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
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
