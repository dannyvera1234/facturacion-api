package com.facturacion.ideas.api.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProductEditDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long ide;

    private String codePrivate;

    private String codeAuxilar;

    private String typeProductEnum;

    private String name;

    private double unitValue;

    private String iva;

    private String ice;

    private String irbpnr;

    private List<ProductInformationDTO> productInformationDTOs = new ArrayList<>();

    public ProductEditDTO(Long ide, String codePrivate, String codeAuxilar, String typeProductEnum,
                          String name, double unitValue, String iva, String ice, String irbpnr,
                          List<ProductInformationDTO> productInformationDTOs) {
        this.ide = ide;
        this.codePrivate = codePrivate;
        this.codeAuxilar = codeAuxilar;
        this.typeProductEnum = typeProductEnum;
        this.name = name;
        this.unitValue = unitValue;
        this.iva = iva;
        this.ice = ice;
        this.irbpnr = irbpnr;
        this.productInformationDTOs = productInformationDTOs;
    }

    public ProductEditDTO() {
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

    public String getCodeAuxilar() {
        return codeAuxilar;
    }

    public void setCodeAuxilar(String codeAuxilar) {
        this.codeAuxilar = codeAuxilar;
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

    public String getIva() {
        return iva;
    }

    public void setIva(String iva) {
        this.iva = iva;
    }

    public String getIce() {
        return ice;
    }

    public void setIce(String ice) {
        this.ice = ice;
    }

    public String getIrbpnr() {
        return irbpnr;
    }

    public void setIrbpnr(String irbpnr) {
        this.irbpnr = irbpnr;
    }

    public List<ProductInformationDTO> getProductInformationDTOs() {
        return productInformationDTOs;
    }

    public void setProductInformationDTOs(List<ProductInformationDTO> productInformationDTOs) {
        this.productInformationDTOs = productInformationDTOs;
    }

    @Override
    public String toString() {
        return "ProductEditDTO{" +
                "ide=" + ide +
                ", codePrivate='" + codePrivate + '\'' +
                ", codeAuxilar='" + codeAuxilar + '\'' +
                ", typeProductEnum='" + typeProductEnum + '\'' +
                ", name='" + name + '\'' +
                ", unitValue=" + unitValue +
                ", iva='" + iva + '\'' +
                ", ice='" + ice + '\'' +
                ", irbpnr='" + irbpnr + '\'' +
                ", productInformationDTOs=" + productInformationDTOs.size() +
                '}';
    }
}
