package com.facturacion.ideas.api.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.List;

public enum TypePaymentEnum {

    SIN_SISTEMA_FINANCIERTO("01", "SIN UTILIZACION DEL SISTEMA FINANCIERO", "2013-01-01", null),
    COMPENSACION_DEUDAS("15", "COMPENSACIÓN DE DEUDAS", "2013-01-01", null),
    TARJETA_DEBITO("16", "TARJETA DE DÉBITO", "2016-06-01", null),
    DINERO_ELECTRONICO("17", "DINERO ELECTRÓNICO", "2016-06-01", null),

    TARJETA_PREPAGO("18", "TARJETA PREPAGO", "2016-06-01", null),
    TARJETA_CREDITO("19", "TARJETA DE CRÉDITO", "2016-06-01", null),
    OTROS_UTILIZACION_SISTEMA_FINANCIERO("20", "OTROS CON UTILIZACION DEL SISTEMA FINANCIERO2", "2016-06-01", null),
    ENDOSO_TITULOS("21", "ENDOSO DE TÍTULOS", "2016-06-01", null);


    private String codigo;
    private String descripcion;
    private String fechaInicio;
    private String fechaFin;


    private TypePaymentEnum(String codigo, String descripcion, String fechaInicio, String fechaFin) {

        this.codigo = codigo;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    @JsonValue
    public String getCodigo() {
        return codigo;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public String getDescripcion() {
        return descripcion;
    }


    public static TypePaymentEnum getTypePaymentEnum(String codigo) {

        if (codigo == null) return null;
        return getListTypePaymentEnum().stream().filter(item -> item.getCodigo().equalsIgnoreCase(codigo)).findFirst().orElse(null);


    }


    public static List<TypePaymentEnum> getListTypePaymentEnum() {

        return List.of(TypePaymentEnum.SIN_SISTEMA_FINANCIERTO, TypePaymentEnum.COMPENSACION_DEUDAS, TypePaymentEnum.DINERO_ELECTRONICO,

                TypePaymentEnum.TARJETA_CREDITO, TypePaymentEnum.TARJETA_DEBITO, TypePaymentEnum.ENDOSO_TITULOS, TypePaymentEnum.OTROS_UTILIZACION_SISTEMA_FINANCIERO,
                TypePaymentEnum.TARJETA_PREPAGO);


    }

}
