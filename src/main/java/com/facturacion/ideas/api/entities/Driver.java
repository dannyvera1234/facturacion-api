package com.facturacion.ideas.api.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.facturacion.ideas.api.enums.TypeIdentificationEnum;

@Entity
@Table(name = "TRANSPORTISTAS")
@PrimaryKeyJoinColumn(referencedColumnName = "PER_COD", name = "TRA_PER")
public class Driver extends Person implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "TRA_PLA")
	private String placa;

	public Driver() {
		super();
	}

	public Driver(Long ide, TypeIdentificationEnum typeIdentification, String numberIdentification, String socialReason,
			String email, String address, String placa) {
		super(ide, typeIdentification, numberIdentification, socialReason, email, address);
		this.placa = placa;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	@Override
	public String toString() {
		return "Driver [placa=" + placa + ", toString()=" + super.toString() + "]";
	}

}
