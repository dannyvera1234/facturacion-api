package com.facturacion.ideas.api.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "FACTURAS")
@PrimaryKeyJoinColumn(referencedColumnName = "DOC_COD", name = "FAC_DOC")
public class Invoice extends Document implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "FAC_GUI_REM")
	private String guiaRemission;

	// En DetailsInvocePayment se agregar el id de esta factura
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "DFP_FK_COD_FAC")
	private List<DetailsInvoicePayment> detailsInvoicePayments;

	// Aqui en facturas se agregar el ide de un registro de ValueInvoice
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "FAC_FK_COD_FVL")
	private ValueInvoice valueInvoice;

	// en DeatailsInvoiceProduct se agregar el ide de esta factura
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "DTF_FK_COD_FAC")
	private List<DeatailsInvoiceProduct> deatailsInvoiceProducts;

	public Invoice() {
		super();
	}

	public Invoice(String guiaRemission) {
		super();
		this.guiaRemission = guiaRemission;
	}

	public String getGuiaRemission() {
		return guiaRemission;
	}

	public void setGuiaRemission(String guiaRemission) {
		this.guiaRemission = guiaRemission;
	}

	public ValueInvoice getValueInvoice() {
		return valueInvoice;
	}

	public void setValueInvoice(ValueInvoice valueInvoice) {
		this.valueInvoice = valueInvoice;
	}

	public List<DetailsInvoicePayment> getDetailsInvoicePayments() {
		return detailsInvoicePayments;
	}

	public void addDetailsInvoicePayments(DetailsInvoicePayment detailsInvoicePayment) {
		this.detailsInvoicePayments.add(detailsInvoicePayment);
	}

	public List<DeatailsInvoiceProduct> getDeatailsInvoiceProducts() {
		return deatailsInvoiceProducts;
	}

	public void addDeatailsInvoiceProducts(DeatailsInvoiceProduct deatailsInvoiceProduct) {

		this.deatailsInvoiceProducts.add(deatailsInvoiceProduct);
	}

	@Override
	public String toString() {
		return "Invoice [guiaRemission=" + guiaRemission + ", toString()=" + super.toString() + "]";
	}

}
