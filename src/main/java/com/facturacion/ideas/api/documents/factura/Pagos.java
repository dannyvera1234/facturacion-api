package com.facturacion.ideas.api.documents.factura;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pagos", propOrder = {"pago"})
public class Pagos {

    @XmlElement(required = true)
    private List<DetallePago> pago;

    public List<DetallePago> getPago() {

        if (this.pago == null) {
            this.pago = new ArrayList<>();
        }

        return pago;
    }

    public void setPago(List<DetallePago> pago) {
        this.pago = pago;
    }
}