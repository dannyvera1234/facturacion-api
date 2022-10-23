package com.facturacion.ideas.api.documents;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "infoTributaria", propOrder = { "ambiente", "tipoEmision", "razonSocial", "nombreComercial", "ruc",
		"claveAcceso", "codDoc", "estab", "ptoEmi", "secuencial", "dirMatriz", "agenteRetencion",
		"contribuyenteRimpe" })
public class InfoTributaria {

	@XmlElement(required = true)
	private String ambiente;

	@XmlElement(required = true)
	private String tipoEmision;

	@XmlElement(required = true)
	private String razonSocial;

	private String nombreComercial;

	@XmlElement(required = true)
	private String ruc;

	@XmlElement(required = true)
	private String claveAcceso;

	@XmlElement(required = true)
	private String codDoc;

	@XmlElement(required = true)
	private String estab;

	@XmlElement(required = true)
	private String ptoEmi;

	@XmlElement(required = true)
	private String secuencial;

	@XmlElement(required = true)
	private String dirMatriz;

	private String agenteRetencion;

	private String contribuyenteRimpe;

	public String getAmbiente() {
		return ambiente;
	}

	public void setAmbiente(String ambiente) {
		this.ambiente = ambiente;
	}

	public String getTipoEmision() {
		return tipoEmision;
	}

	public void setTipoEmision(String tipoEmision) {
		this.tipoEmision = tipoEmision;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getNombreComercial() {
		return nombreComercial;
	}

	public void setNombreComercial(String nombreComercial) {
		this.nombreComercial = nombreComercial;
	}

	public String getRuc() {
		return ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}

	public String getClaveAcceso() {
		return claveAcceso;
	}

	public void setClaveAcceso(String claveAcceso) {
		this.claveAcceso = claveAcceso;
	}

	public String getCodDoc() {
		return codDoc;
	}

	public void setCodDoc(String codDoc) {
		this.codDoc = codDoc;
	}

	public String getEstab() {
		return estab;
	}

	public void setEstab(String estab) {
		this.estab = estab;
	}

	public String getPtoEmi() {
		return ptoEmi;
	}

	public void setPtoEmi(String ptoEmi) {
		this.ptoEmi = ptoEmi;
	}

	public String getSecuencial() {
		return secuencial;
	}

	public void setSecuencial(String secuencial) {
		this.secuencial = secuencial;
	}

	public String getDirMatriz() {
		return dirMatriz;
	}

	public void setDirMatriz(String dirMatriz) {
		this.dirMatriz = dirMatriz;
	}

	public String getAgenteRetencion() {
		return agenteRetencion;
	}

	public void setAgenteRetencion(String agenteRetencion) {
		this.agenteRetencion = agenteRetencion;
	}

	public String getContribuyenteRimpe() {
		return contribuyenteRimpe;
	}

	public void setContribuyenteRimpe(String contribuyenteRimpe) {
		this.contribuyenteRimpe = contribuyenteRimpe;
	}
	
	
	

}
