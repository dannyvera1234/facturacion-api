package com.facturacion.ideas.api.documents.factura;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "compensaciones", propOrder = {"compensacion"})
public class Compensaciones {

	@XmlElement(required = true)
	private List<DetalleCompensacion> compensacion;

	
	public List<DetalleCompensacion> getCompensacion() {
		if (this.compensacion ==null) 
			this.compensacion = new ArrayList<>();
		return compensacion;
	}
	
	public void setCompensacion(List<DetalleCompensacion> compensacion) {
		this.compensacion = compensacion;
	}
}
