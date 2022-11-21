package com.facturacion.ideas.api.dto;

import com.facturacion.ideas.api.sri.ws.recepcion.RespuestaSolicitud;

public class ResponseWebServiceDTO {

    private RespuestaSolicitud respuestaSolicitud;


    public RespuestaSolicitud getRespuestaSolicitud() {
        return respuestaSolicitud;
    }

    public void setRespuestaSolicitud(RespuestaSolicitud respuestaSolicitud) {
        this.respuestaSolicitud = respuestaSolicitud;
    }
}
