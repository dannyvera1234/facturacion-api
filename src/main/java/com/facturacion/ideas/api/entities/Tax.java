package com.facturacion.ideas.api.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.facturacion.ideas.api.enums.TypeTaxEnum;

@Entity
@Table(name = "impuestos")
public class Tax implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IMP_COD")
	private Long ide;

	@Column(name = "IMP_TAIM_DES")
	private String typeTax;

	@Column(name = "IMP_TAIM_EST")
	private String status;

	public Tax(Long ide, TypeTaxEnum typeTax, String status) {
		super();
		this.ide = ide;
		this.typeTax = typeTax.getCode();
		this.status = status;

	}

	public Long getIde() {
		return ide;
	}

	public void setIde(Long ide) {
		this.ide = ide;
	}

	public String getTypeTax() {
		return typeTax;
	}

	public void setTypeTax(TypeTaxEnum typeTax) {
		this.typeTax = typeTax.getCode();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Tax [ide=" + ide + ", typeTax=" + typeTax + ", status=" + status + "]";
	}

}
