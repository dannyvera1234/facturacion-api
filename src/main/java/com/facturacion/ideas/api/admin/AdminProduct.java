package com.facturacion.ideas.api.admin;

import com.facturacion.ideas.api.dto.ProductInformationDTO;
import com.facturacion.ideas.api.entities.Product;
import com.facturacion.ideas.api.entities.TaxValue;
import com.facturacion.ideas.api.enums.QuestionEnum;
import com.facturacion.ideas.api.enums.TypePorcentajeIvaEnum;
import com.facturacion.ideas.api.enums.TypeTaxEnum;
import com.facturacion.ideas.api.exeption.BadRequestException;
import com.facturacion.ideas.api.util.ConstanteUtil;


import java.util.List;

public class AdminProduct {


    public static final int numeroInfoAdicional = 3;

    /**
     * Verificar que la informacion adicional no exeda en {@link #numeroInfoAdicional}
     *
     * @param productInformationDTOList : lista de informacion adicional
     */
    public static void numeroMaximoInfoAdicionalProduct(List<ProductInformationDTO> productInformationDTOList) {
        int listSize = productInformationDTOList.size();

        if (listSize > numeroInfoAdicional)
            throw new BadRequestException("Cantidad información adicional  producto exceden a 3 ; cantidad: "
                    + listSize);
    }

    public static void asignarSiNoImpuesto(TypeTaxEnum typeTaxEnum, Product product) {

        if (typeTaxEnum.getCode().equals(TypeTaxEnum.IVA.getCode())) {
            asignarSiNoIVA(product);
        }

        if (typeTaxEnum.getCode().equals(TypeTaxEnum.ICE.getCode())) {
            asignarSiNoICE(product);
        }

        if (typeTaxEnum.getCode().equals(TypeTaxEnum.IRBPNR.getCode())) {
            asignarSiNoIRBPNR(product);
        }

    }

    private static void asignarSiNoIVA(Product product) {

        if (product.getIva() != null) {
            // Solo si  iva corresponde a iva del 12%, decimos que si graba iva
            // de lo contrario, decimos que no graba
            boolean gravaIva = product.getIva().equalsIgnoreCase(TypePorcentajeIvaEnum.IVA_DOCE.getCode());
            product.setIva(gravaIva
                    ? QuestionEnum.SI.name() : QuestionEnum.NO.name()); // Valor por defecto

        } else product.setIva(QuestionEnum.NO.name()); // Valor por defecto
    }


    private static void asignarSiNoICE(Product product) {
        if (product.getIce() != null) {

            // code del impuesto valor
            String codeImpuesto = product.getIce();

            product.setIce(QuestionEnum.SI.name());

        } else product.setIce(QuestionEnum.NO.name()); // Valor por defecto

    }

    private static void asignarSiNoIRBPNR(Product product) {

        if (product.getIrbpnr() != null) {

            // code del impuesto valor
            String codeImpuesto = product.getIrbpnr();

            product.setIrbpnr(QuestionEnum.SI.name());

        } else product.setIrbpnr(QuestionEnum.NO.name()); // Valor por defecto
    }


    /**
     * Calcula el nuevo precio del producto, aplicando los impuestos IVA, ICE, IBPNR.
     *
     * @param typeTaxEnum
     * @param taxValue
     * @param product
     */
    public static void calcularNuevoPrecioProdImpuesto(TypeTaxEnum typeTaxEnum, TaxValue taxValue, Product product) {

        String codeTax = typeTaxEnum.getCode();
        double precioActual = product.getUnitValue();

        double valorIva = 0.0;

        if (codeTax.equals(TypeTaxEnum.IVA.getCode())) {

            // Determinar el tipo de IVA
            if (taxValue.getCode().equalsIgnoreCase(TypePorcentajeIvaEnum.IVA_DOCE.getCode())) {
                valorIva = (precioActual * TypePorcentajeIvaEnum.IVA_DOCE.getPorcentaje()) / 100;

            } else if (taxValue.getCode().equalsIgnoreCase(TypePorcentajeIvaEnum.IVA_CATORCE.getCode())) {
                valorIva = (precioActual * TypePorcentajeIvaEnum.IVA_CATORCE.getPorcentaje()) / 100;
            }

        } else if (codeTax.equals(TypeTaxEnum.ICE.getCode())) {

            Double porcentaje = taxValue.getPorcentage();

            if (!porcentaje.isNaN())
                valorIva = (precioActual * porcentaje) / 100;
        } else if (codeTax.equals(TypeTaxEnum.IRBPNR.getCode())) {

            // Por cada botella retornable se agrerá 0.02 de dolar.
            // el porcetaje aplicado en impuesto_valor es 0 %,  por ello aqui sumamos directamente
            // los 0.02 al precio actual, y no aplicamosla formula de porcentaje

            // Este valor lo aplicamos directamente, en el calculo de los valores de
            // la factura
            // valorIva = precioActual + ConstanteUtil.VALOR_IVA_IRBPNR;
        }

        precioActual += valorIva;

        // Asignar nuevo precio aplicado iva
        product.setUnitValue(precioActual);
    }


}
