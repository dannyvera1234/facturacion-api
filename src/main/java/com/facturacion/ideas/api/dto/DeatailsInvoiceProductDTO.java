package com.facturacion.ideas.api.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class DeatailsInvoiceProductDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long ide;

    private String description;

    private Double unitValue;

    private double amount;

    // Este es opcional
    private double valorIce;

    private double subtotal;

    /**
     * Vendra el codigo del producto, para relacionarlo con la factura , y obtener el tipo de servicio y precio
     */
    private Long idProducto;

    public DeatailsInvoiceProductDTO() {
    }


    public DeatailsInvoiceProductDTO(Long ide, double amount, double subtotal, Long idProducto) {
        this.ide = ide;
        this.amount = amount;
        this.subtotal = subtotal;
        this.idProducto = idProducto;
    }

    public Long getIde() {
        return ide;
    }

    public void setIde(Long ide) {
        this.ide = ide;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public Long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getUnitValue() {
        return unitValue;
    }

    public void setUnitValue(Double unitValue) {
        this.unitValue = unitValue;
    }

    public double getValorIce() {
        return valorIce;
    }

    public void setValorIce(double valorIce) {
        this.valorIce = valorIce;
    }


    @Override
    public String toString() {
        return "DeatailsInvoiceProductDTO{" +
                "ide=" + ide +
                ", description='" + description + '\'' +
                ", unitValue=" + unitValue +
                ", amount=" + amount +
                ", subtotal=" + subtotal +
                ", idProducto=" + idProducto +
                '}';
    }
}
