package com.facturacion.ideas.api.documents.factura;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "detallesAdicionales", propOrder = { "detAdicional" })
public class DetallesAdicionales {

    @XmlElement(required = true)
    private List<DetAdicional> detAdicional;

    public List<DetAdicional> getDetAdicional() {
        if (this.detAdicional == null)
            this.detAdicional = new ArrayList<>();

        return detAdicional;
    }

    public void setDetAdicional(List<DetAdicional> detAdicional) {
        this.detAdicional = detAdicional;
    }

}