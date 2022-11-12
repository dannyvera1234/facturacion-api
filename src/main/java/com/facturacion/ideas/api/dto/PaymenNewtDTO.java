package com.facturacion.ideas.api.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class PaymenNewtDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String code;

    private BigDecimal total;

    private String plazo;

    private String unidadTiempo;

    public PaymenNewtDTO(String code, BigDecimal total, String plazo, String unidadTiempo) {
        this.code = code;
        this.total = total;
        this.plazo = plazo;
        this.unidadTiempo = unidadTiempo;
    }

    public PaymenNewtDTO() {
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getPlazo() {
        return plazo;
    }

    public void setPlazo(String plazo) {
        this.plazo = plazo;
    }

    public String getUnidadTiempo() {
        return unidadTiempo;
    }

    public void setUnidadTiempo(String unidadTiempo) {
        this.unidadTiempo = unidadTiempo;
    }
}
