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
@Table(name = "detalle_factura")
public class DeatailsInvoiceProduct implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DTF_COD")
	private Long ide;

	@Column(name = " DTF_CAN")
	private double amount;

	@Column(name = " DTF_SUB")
	private double subtotal;

	// Aqui se crear una refrencia hacia un product
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DTF_FK_COD_PRO")
	private Product product;

	public DeatailsInvoiceProduct() {
		super();
	}

	public DeatailsInvoiceProduct(Long ide, double amount, double subtotal, Product product) {
		super();
		this.ide = ide;
		this.amount = amount;
		this.subtotal = subtotal;
		this.product = product;
	}

	public Long getIde() {
		return ide;
	}

	public void setIde(Long ide) {
		this.ide = ide;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Override
	public String toString() {
		return "DeatailsInvoiceProduct [ide=" + ide + ", amount=" + amount + ", subtotal=" + subtotal + "]";
	}

}