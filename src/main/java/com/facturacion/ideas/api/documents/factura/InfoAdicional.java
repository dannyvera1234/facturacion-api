package com.facturacion.ideas.api.documents.factura;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "infoAdicional", propOrder = { "campoAdicional" })
public class InfoAdicional {

	@XmlElement(required = true)
	private List<CampoAdicional> campoAdicional;

	public List<CampoAdicional> getCampoAdicional() {

		if (this.campoAdicional == null)
			this.campoAdicional = new ArrayList<>();
		return campoAdicional;
	}

	public void setCampoAdicional(List<CampoAdicional> campoAdicional) {
		this.campoAdicional = campoAdicional;
	}
}
