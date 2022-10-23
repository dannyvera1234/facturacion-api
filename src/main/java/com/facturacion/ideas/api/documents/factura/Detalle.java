package com.facturacion.ideas.api.documents.factura;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "detalle", propOrder = {"codigoPrincipal", "codigoAuxiliar", "descripcion", "cantidad", "precioUnitario", "precioSinSubsidio", "descuento", "precioTotalSinImpuesto", "detallesAdicionales", "impuestos"})
public class Detalle {

	@XmlElement(required = true)
    private String codigoPrincipal;
    
    private String codigoAuxiliar;
    
    @XmlElement(required = true)
    private String descripcion;
    
    @XmlElement(required = true)
    private BigDecimal cantidad;
    
    @XmlElement(required = true)
    private BigDecimal precioUnitario;
    
    @XmlElement(required = true)
    private BigDecimal precioSinSubsidio;
    
    @XmlElement(required = true)
    private BigDecimal descuento;
    
    @XmlElement(required = true)
    private BigDecimal precioTotalSinImpuesto;
   
    private DetallesAdicionales detallesAdicionales;
    
    @XmlElement(required = true)
	private Impuestos impuestos;

	public String getCodigoPrincipal() {
		return codigoPrincipal;
	}

	public void setCodigoPrincipal(String codigoPrincipal) {
		this.codigoPrincipal = codigoPrincipal;
	}

	public String getCodigoAuxiliar() {
		return codigoAuxiliar;
	}

	public void setCodigoAuxiliar(String codigoAuxiliar) {
		this.codigoAuxiliar = codigoAuxiliar;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public BigDecimal getCantidad() {
		return cantidad;
	}

	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}

	public BigDecimal getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(BigDecimal precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public BigDecimal getPrecioSinSubsidio() {
		return precioSinSubsidio;
	}

	public void setPrecioSinSubsidio(BigDecimal precioSinSubsidio) {
		this.precioSinSubsidio = precioSinSubsidio;
	}

	public BigDecimal getDescuento() {
		return descuento;
	}

	public void setDescuento(BigDecimal descuento) {
		this.descuento = descuento;
	}

	public BigDecimal getPrecioTotalSinImpuesto() {
		return precioTotalSinImpuesto;
	}

	public void setPrecioTotalSinImpuesto(BigDecimal precioTotalSinImpuesto) {
		this.precioTotalSinImpuesto = precioTotalSinImpuesto;
	}

	public DetallesAdicionales getDetallesAdicionales() {
		return detallesAdicionales;
	}

	public void setDetallesAdicionales(DetallesAdicionales detallesAdicionales) {
		this.detallesAdicionales = detallesAdicionales;
	}

	public Impuestos getImpuestos() {
		return impuestos;
	}

	public void setImpuestos(Impuestos impuestos) {
		this.impuestos = impuestos;
	}
    
    
}
