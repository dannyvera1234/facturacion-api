package com.facturacion.ideas.api.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "FACTURA_VALORES")
public class ValueInvoice implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "FVL_COD")
	private Long ide;

	@Column(name = " FVL_SUB_IVA_ACT")
	private double subtIvaActual;

	@Column(name = " FVL_SUB_IVA_CER")
	private double subtIvaCero;

	@Column(name = "FVL_SUB_NOT_OBJ_IVA")
	private double subtNoObjIva;

	@Column(name = "FVL_SUB_EXE_IVA")
	private double subtExceptoIva;

	@Column(name = "FVL_SUB")
	private double subtotal;

	@Column(name = "FVL_DES")
	private double descuento;

	@Column(name = "FVL_ICE")
	private double ice;

	@Column(name = "FVL_RBP")
	private double rbpnr;

	@Column(name = "FVL_IVA_ACT")
	private double iva;

	@Column(name = "FVL_PRO")
	private double propina;

	@Column(name = "FVL_TOT")
	private double total;

	public ValueInvoice() {
		super();
	}

	public ValueInvoice(Long ide, double subtIvaActual, double subtIvaCero, double subtNoObjIva, double subtExceptoIva,
			double subtotal, double descuento, double ice, double rbpnr, double iva, double propina, double total) {
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
		return "ValueInvoice [ide=" + ide + ", subtIvaActual=" + subtIvaActual + ", subtIvaCero=" + subtIvaCero
				+ ", subtNoObjIva=" + subtNoObjIva + ", subtExceptoIva=" + subtExceptoIva + ", subtotal=" + subtotal
				+ ", descuento=" + descuento + ", ice=" + ice + ", rbpnr=" + rbpnr + ", iva=" + iva + ", propina="
				+ propina + ", total=" + total + "]";
	}

}
