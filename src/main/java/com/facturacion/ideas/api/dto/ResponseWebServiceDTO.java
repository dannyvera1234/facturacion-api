package com.facturacion.ideas.api.dto;

import com.facturacion.ideas.api.sri.ws.autorizacion.RespuestaComprobante;
import com.facturacion.ideas.api.sri.ws.recepcion.RespuestaSolicitud;

public class ResponseWebServiceDTO {

    private RespuestaSolicitud respuestaSolicitud;

    private RespuestaComprobante respuestaComprobante;

    private  ValueInvoiceNewDTO valueInvoiceNewDTO;


    public RespuestaSolicitud getRespuestaSolicitud() {
        return respuestaSolicitud;
    }

    public void setRespuestaSolicitud(RespuestaSolicitud respuestaSolicitud) {
        this.respuestaSolicitud = respuestaSolicitud;
    }

    public RespuestaComprobante getRespuestaComprobante() {
        return respuestaComprobante;
    }

    public void setRespuestaComprobante(RespuestaComprobante respuestaComprobante) {
        this.respuestaComprobante = respuestaComprobante;
    }

    public ValueInvoiceNewDTO getValueInvoiceNewDTO() {
        return valueInvoiceNewDTO;
    }

    public void setValueInvoiceNewDTO(ValueInvoiceNewDTO valueInvoiceNewDTO) {
        this.valueInvoiceNewDTO = valueInvoiceNewDTO;
    }
}
