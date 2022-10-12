package com.facturacion.ideas.api.dto;

import java.io.Serializable;

public class ValueInvoiceNewDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long ide;

	private double subtIvaActual;

	private double subtIvaCero;

	private double subtNoObjIva;

	private double subtExceptoIva;

	private double subtotal;

	private double descuento;

	private double ice;

	private double rbpnr;

	private double iva;

	private double propina;

	private double total;

	public ValueInvoiceNewDTO() {
		super();
	}

	public ValueInvoiceNewDTO(Long ide, double subtIvaActual, double subtIvaCero, double subtNoObjIva,
			double subtExceptoIva, double subtotal, double descuento, double ice, double rbpnr, double iva,
			double propina, double total) {
		super();
		this.ide = ide;
		this.subtIvaActual = subtIvaActual;
		this.subtIvaCero = subtIvaCero;
		this.subtNoObjIva = subtNoObjIva;
		this.subtExceptoIva = subtExceptoIva;
		this.subtotal = subtotal;
		this.descuento = descuento;
		this.ice = ice;
		this.rbpnr = rbpnr;
		this.iva = iva;
		this.propina = propina;
		this.total = total;
	}

	public Long getIde() {
		return ide;
	}

	public void setIde(Long ide) {
		this.ide = ide;
	}

	public double getSubtIvaActual() {
		return subtIvaActual;
	}

	public void setSubtIvaActual(double subtIvaActual) {
		this.subtIvaActual = subtIvaActual;
	}

	public double getSubtIvaCero() {
		return subtIvaCero;
	}

	public void setSubtIvaCero(double subtIvaCero) {
		this.subtIvaCero = subtIvaCero;
	}

	public double getSubtNoObjIva() {
		return subtNoObjIva;
	}

	public void setSubtNoObjIva(double subtNoObjIva) {
		this.subtNoObjIva = subtNoObjIva;
	}

	public double getSubtExceptoIva() {
		return subtExceptoIva;
	}

	public void setSubtExceptoIva(double subtExceptoIva) {
		this.subtExceptoIva = subtExceptoIva;
	}

	public double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}

	public double getDescuento() {
		return descuento;
	}

	public void setDescuento(double descuento) {
		this.descuento = descuento;
	}

	public double getIce() {
		return ice;
	}

	public void setIce(double ice) {
		this.ice = ice;
	}

	public double getRbpnr() {
		return rbpnr;
	}

	public void setRbpnr(double rbpnr) {
		this.rbpnr = rbpnr;
	}

	public double getIva() {
		return iva;
	}

	public void setIva(double iva) {
		this.iva = iva;
	}

	public double getPropina() {
		return propina;
	}

	public void setPropina(double propina) {
		this.propina = propina;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "ValueInvoiceNewDTO [ide=" + ide + ", subtIvaActual=" + subtIvaActual + ", subtIvaCero=" + subtIvaCero
				+ ", subtNoObjIva=" + subtNoObjIva + ", subtExceptoIva=" + subtExceptoIva + ", subtotal=" + subtotal
				+ ", descuento=" + descuento + ", ice=" + ice + ", rbpnr=" + rbpnr + ", iva=" + iva + ", propina="
				+ propina + ", total=" + total + "]";
	}

}
