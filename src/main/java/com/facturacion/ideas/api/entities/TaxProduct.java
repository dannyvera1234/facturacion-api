package com.facturacion.ideas.api.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PRODUCTO_IMPUESTO")
public class TaxProduct implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PDI_COD")
	private Long ide;

	/*
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PDI_FK_COD_PRO")
	private Product product;
	*/
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PDI_FK_COD_IMP_VAL")
	private TaxValue taxValue;

	public TaxProduct() {
		super();
	}

	public TaxProduct(Long ide, Product product, TaxValue taxValue) {
		super();
		this.ide = ide;
		//this.product = product;
		this.taxValue = taxValue;
	}

	public TaxProduct(Product product, TaxValue taxValue) {
		//this.product = product;
		this.taxValue = taxValue;
	}

	public Long getIde() {
		return ide;
	}

	public void setIde(Long ide) {
		this.ide = ide;
	}

	/*
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}*/

	public TaxValue getTaxValue() {
		return taxValue;
	}

	public void setTaxValue(TaxValue taxValue) {
		this.taxValue = taxValue;
	}

	/*
	@Override
	public String toString() {
		return "TaxProduct [ide=" + ide + ", product=" + product.getIde() + ", taxValue=" + taxValue.getIde() + "]";
	}*/

	@Override
	public String toString() {
		return "TaxProduct{" +
				"ide=" + ide +
				", taxValue=" + taxValue.getIde() +
				'}';
	}
}
