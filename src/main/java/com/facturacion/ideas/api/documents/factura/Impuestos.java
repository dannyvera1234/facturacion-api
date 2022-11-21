

package com.facturacion.ideas.api.documents.factura;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "impuestos", propOrder = { "impuesto" })
public class Impuestos {

    @XmlElement(required = true)
    private List<Impuesto> impuesto;

    public List<Impuesto> getImpuesto() {

        if (this.impuesto == null)
            this.impuesto = new ArrayList<>();

        return impuesto;
    }

    public void setImpuesto(List<Impuesto> impuesto) {
        this.impuesto = impuesto;
    }
}