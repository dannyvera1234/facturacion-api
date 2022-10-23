package com.facturacion.ideas.api.documents.factura;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "totalImpuesto", propOrder = {"codigo", "codigoPorcentaje", "baseImponible", "tarifa", "valor"})
public class TotalImpuesto {
	
	@XmlElement(required = true)
    private String codigo;
    
    @XmlElement(required = true)
    private String codigoPorcentaje;
    
    @XmlElement(required = true)
    private BigDecimal baseImponible;
    
    private BigDecimal tarifa;
    
    @XmlElement(required = true)
    private BigDecimal valor;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getCodigoPorcentaje() {
		return codigoPorcentaje;
	}

	public void setCodigoPorcentaje(String codigoPorcentaje) {
		this.codigoPorcentaje = codigoPorcentaje;
	}

	public BigDecimal getBaseImponible() {
		return baseImponible;
	}

	public void setBaseImponible(BigDecimal baseImponible) {
		this.baseImponible = baseImponible;
	}

	public BigDecimal getTarifa() {
		return tarifa;
	}

	public void setTarifa(BigDecimal tarifa) {
		this.tarifa = tarifa;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
    
    
    
	
	
}
