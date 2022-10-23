package com.facturacion.ideas.api.documents.factura;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "detalles", propOrder = { "detalle" })
public class Detalles {

	@XmlElement(required = true)
	private List<Detalle> detalle;

	public List<Detalle> getDetalle() {
		if (this.detalle == null)
			this.detalle = new ArrayList<>();

		return detalle;
	}

	public void setDetalle(List<Detalle> detalle) {
		this.detalle = detalle;
	}
}
