package com.facturacion.ideas.api.dto;

import java.io.Serializable;

public class ComprobantesResponseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idComprobante;
    private String nombreCliente;
    private String numeroIdentificacion;
    private String tipoDocumento;
    private String fechaEmision;
    private double totalDocumento;

    public ComprobantesResponseDTO() {
    }

    public ComprobantesResponseDTO(Long idComprobante, String nombreCliente, String numeroIdentificacion, String tipoDocumento, String fechaEmision, double totalDocumento) {
        this.idComprobante = idComprobante;
        this.nombreCliente = nombreCliente;
        this.numeroIdentificacion = numeroIdentificacion;
        this.tipoDocumento = tipoDocumento;
        this.fechaEmision = fechaEmision;
        this.totalDocumento = totalDocumento;
    }

    public Long getIdComprobante() {
        return idComprobante;
    }

    public void setIdComprobante(Long idComprobante) {
        this.idComprobante = idComprobante;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(String fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public double getTotalDocumento() {
        return totalDocumento;
    }

    public void setTotalDocumento(double totalDocumento) {
        this.totalDocumento = totalDocumento;
    }

    @Override
    public String toString() {
        return "ComprobantesResponseDTO{" +
                "idComprobante=" + idComprobante +
                ", nombreCliente='" + nombreCliente + '\'' +
                ", numeroIdentificacion='" + numeroIdentificacion + '\'' +
                ", tipoDocumento='" + tipoDocumento + '\'' +
                ", fechaEmision='" + fechaEmision + '\'' +
                ", totalDocumento=" + totalDocumento +
                '}';
    }
}
