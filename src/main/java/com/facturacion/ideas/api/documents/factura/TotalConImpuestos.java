package com.facturacion.ideas.api.documents.factura;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "totalConImpuestos", propOrder = {"totalImpuesto"})
public class TotalConImpuestos {
	
	@XmlElement(required = true)
    private List<TotalImpuesto> totalImpuesto;
    
    public List<TotalImpuesto> getTotalImpuesto() {
      if (this.totalImpuesto == null)
        this.totalImpuesto = new ArrayList<>(); 
      return this.totalImpuesto;
    }

	public void setTotalImpuesto(List<TotalImpuesto> totalImpuesto) {
		this.totalImpuesto = totalImpuesto;
	}
    
	
}
