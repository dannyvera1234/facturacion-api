package com.facturacion.ideas.api.documents.factura;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "infoFactura", propOrder = { "fechaEmision", "dirEstablecimiento", "contribuyenteEspecial",
		"obligadoContabilidad", "tipoIdentificacionComprador", "guiaRemision", "razonSocialComprador",
		"identificacionComprador", "direccionComprador", "totalSinImpuestos", "totalSubsidio", "totalDescuento",
		"totalConImpuestos", "compensaciones", "propina", "importeTotal", "moneda", "pagos" })
public class InfoFactura {

	@XmlElement(required = true)
	private String fechaEmision;

	@XmlElement(required = true)
	private String dirEstablecimiento;

	private String contribuyenteEspecial;

	private String obligadoContabilidad;

	@XmlElement(required = true)
	private String tipoIdentificacionComprador;

	private String guiaRemision;

	@XmlElement(required = true)
	private String razonSocialComprador;

	@XmlElement(required = true)
	private String identificacionComprador;

	private String direccionComprador;

	@XmlElement(required = true)
	private BigDecimal totalSinImpuestos;

	@XmlElement(required = true)
	private BigDecimal totalSubsidio;

	@XmlElement(required = true)
	private BigDecimal totalDescuento;

	@XmlElement(required = true)
	private TotalConImpuestos totalConImpuestos;
	
	private Compensaciones compensaciones;
	
	@XmlElement(required = true)
	private BigDecimal propina;
	
	@XmlElement(required = true)
	private BigDecimal importeTotal;
	
	private String moneda;
	
	private Pagos pago;

	public String getFechaEmision() {
		return fechaEmision;
	}

	public void setFechaEmision(String fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	public String getDirEstablecimiento() {
		return dirEstablecimiento;
	}

	public void setDirEstablecimiento(String dirEstablecimiento) {
		this.dirEstablecimiento = dirEstablecimiento;
	}

	public String getContribuyenteEspecial() {
		return contribuyenteEspecial;
	}

	public void setContribuyenteEspecial(String contribuyenteEspecial) {
		this.contribuyenteEspecial = contribuyenteEspecial;
	}

	public String getObligadoContabilidad() {
		return obligadoContabilidad;
	}

	public void setObligadoContabilidad(String obligadoContabilidad) {
		this.obligadoContabilidad = obligadoContabilidad;
	}

	public String getTipoIdentificacionComprador() {
		return tipoIdentificacionComprador;
	}

	public void setTipoIdentificacionComprador(String tipoIdentificacionComprador) {
		this.tipoIdentificacionComprador = tipoIdentificacionComprador;
	}

	public String getGuiaRemision() {
		return guiaRemision;
	}

	public void setGuiaRemision(String guiaRemision) {
		this.guiaRemision = guiaRemision;
	}

	public String getRazonSocialComprador() {
		return razonSocialComprador;
	}

	public void setRazonSocialComprador(String razonSocialComprador) {
		this.razonSocialComprador = razonSocialComprador;
	}

	public String getIdentificacionComprador() {
		return identificacionComprador;
	}

	public void setIdentificacionComprador(String identificacionComprador) {
		this.identificacionComprador = identificacionComprador;
	}

	public String getDireccionComprador() {
		return direccionComprador;
	}

	public void setDireccionComprador(String direccionComprador) {
		this.direccionComprador = direccionComprador;
	}

	public BigDecimal getTotalSinImpuestos() {
		return totalSinImpuestos;
	}

	public void setTotalSinImpuestos(BigDecimal totalSinImpuestos) {
		this.totalSinImpuestos = totalSinImpuestos;
	}

	public BigDecimal getTotalSubsidio() {
		return totalSubsidio;
	}

	public void setTotalSubsidio(BigDecimal totalSubsidio) {
		this.totalSubsidio = totalSubsidio;
	}

	public BigDecimal getTotalDescuento() {
		return totalDescuento;
	}

	public void setTotalDescuento(BigDecimal totalDescuento) {
		this.totalDescuento = totalDescuento;
	}

	public TotalConImpuestos getTotalConImpuestos() {
		return totalConImpuestos;
	}

	public void setTotalConImpuestos(TotalConImpuestos totalConImpuestos) {
		this.totalConImpuestos = totalConImpuestos;
	}

	public Compensaciones getCompensaciones() {
		return compensaciones;
	}

	public void setCompensaciones(Compensaciones compensaciones) {
		this.compensaciones = compensaciones;
	}

	public BigDecimal getPropina() {
		return propina;
	}

	public void setPropina(BigDecimal propina) {
		this.propina = propina;
	}

	public BigDecimal getImporteTotal() {
		return importeTotal;
	}

	public void setImporteTotal(BigDecimal importeTotal) {
		this.importeTotal = importeTotal;
	}

	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	public Pagos getPago() {
		return pago;
	}

	public void setPago(Pagos pago) {
		this.pago = pago;
	}
	
}
