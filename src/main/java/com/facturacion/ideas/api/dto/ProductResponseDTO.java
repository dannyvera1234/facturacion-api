package com.facturacion.ideas.api.dto;

import java.io.Serializable;
import java.util.Objects;

public class ProductResponseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long ide;

    private String codePrivate;

    //private String codeAuxilar;

    private String typeProductEnum;

    private String name;

    private double unitValue;

    //private String dateCreate;

    //private String iva;

    //private String ice;

    //private String irbpnr;

   // private String sender;


    public ProductResponseDTO() {
    }

    public ProductResponseDTO(Long ide, String codePrivate, String typeProductEnum, String name, double unitValue) {
        this.ide = ide;
        this.codePrivate = codePrivate;
        this.typeProductEnum = typeProductEnum;
        this.name = name;
        this.unitValue = unitValue;
    }


    public Long getIde() {
        return ide;
    }

    public void setIde(Long ide) {
        this.ide = ide;
    }

    public String getCodePrivate() {
        return codePrivate;
    }

    public void setCodePrivate(String codePrivate) {
        this.codePrivate = codePrivate;
    }

    public String getTypeProductEnum() {
        return typeProductEnum;
    }

    public void setTypeProductEnum(String typeProductEnum) {
        this.typeProductEnum = typeProductEnum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getUnitValue() {
        return unitValue;
    }

    public void setUnitValue(double unitValue) {
        this.unitValue = unitValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductResponseDTO that = (ProductResponseDTO) o;
        return Double.compare(that.unitValue, unitValue) == 0 && Objects.equals(ide, that.ide) && Objects.equals(codePrivate, that.codePrivate) && Objects.equals(typeProductEnum, that.typeProductEnum) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ide, codePrivate, typeProductEnum, name, unitValue);
    }

    @Override
    public String toString() {
        return "ProductRespnseDTO{" +
                "ide=" + ide +
                ", codePrivate='" + codePrivate + '\'' +
                ", typeProductEnum='" + typeProductEnum + '\'' +
                ", name='" + name + '\'' +
                ", unitValue=" + unitValue +
                '}';
    }
}
