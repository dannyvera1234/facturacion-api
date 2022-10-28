package com.facturacion.ideas.api.enums;

import com.facturacion.ideas.api.util.ConstanteUtil;

import java.util.Arrays;
import java.util.List;

public enum TypePorcentajeIvaEnum {

    IVA_CERO( "0", "0%", 0.0),
    IVA_DOCE( "2", "12%", ConstanteUtil.IVA_ACTUAL),
    IVA_CATORCE("3", "14%", 14.00),
    IVA_NO_OBJETO( "6", "No Objeto de Impuesto", 0.0),
    IVA_EXENTO("7", "Exento de IVA", 0.0);
    private String description;
    private String code;

    private  Double porcentaje;
    TypePorcentajeIvaEnum(String code, String description, Double porcentaje) {
        this.code = code;
        this.description = description;
        this.porcentaje = porcentaje;


    }

    public String getDescription() {
        return description;
    }

    public String getCode() {
        return code;
    }

    public Double getPorcentaje() {
        return porcentaje;
    }

    public static TypePorcentajeIvaEnum findTpePorcentajeIvaEnum(String code){

        if (code !=null){

        return listTypePorcentajeIvaEnum().stream()
                    .filter(item -> item.getCode().equals(code)).findFirst().orElse(null);
        }
        return null;

    }

   public static List<TypePorcentajeIvaEnum> listTypePorcentajeIvaEnum(){
        return List.of(TypePorcentajeIvaEnum.IVA_CERO,
                TypePorcentajeIvaEnum.IVA_DOCE, TypePorcentajeIvaEnum.IVA_CATORCE,
                TypePorcentajeIvaEnum.IVA_NO_OBJETO, TypePorcentajeIvaEnum.IVA_EXENTO);
   }

}
