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
@Table(name = "DETALLE_FACTURA_PAGOS")
public class DetailsInvoicePayment implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DFP_COD")
	private Long ide;

	/**
	 * Se creara un campo aqui con el metodo de pago
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DFP_FK_COD_PAG")
	private Payment payment;

	public DetailsInvoicePayment() {
		super();
	}

	public DetailsInvoicePayment(Long ide, Payment payment) {
		super();
		this.ide = ide;
		this.payment = payment;
	}

	public Long getIde() {
		return ide;
	}

	public void setIde(Long ide) {
		this.ide = ide;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	@Override
	public String toString() {
		return "DetailsInvoicePayment [ide=" + ide + "]";
	}

}
