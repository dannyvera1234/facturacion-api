package com.facturacion.ideas.api.admin;

import com.facturacion.ideas.api.documents.InfoTributaria;
import com.facturacion.ideas.api.documents.factura.*;
import com.facturacion.ideas.api.dto.DeatailsInvoiceProductDTO;
import com.facturacion.ideas.api.entities.*;
import com.facturacion.ideas.api.enums.QuestionEnum;
import com.facturacion.ideas.api.enums.TypeIdentificationEnum;
import com.facturacion.ideas.api.enums.TypePorcentajeIvaEnum;
import com.facturacion.ideas.api.enums.TypeTaxEnum;
import com.facturacion.ideas.api.exeption.BadRequestException;
import com.facturacion.ideas.api.util.ConstanteUtil;
import com.facturacion.ideas.api.util.FunctionUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AdminInvoice {


    /**
     * Crear una Factura
     *
     * @param invoiceSaved : La informacion de la factura guardada
     * @return : La factura que representara el XML
     */
    public static Factura createFacturaXML(Invoice invoiceSaved, Sender sender) {

        Factura factura = new Factura();

        factura.setInfoTributaria(createInfoTributaria(invoiceSaved, sender));
        factura.setInfoFactura(createInfoFactura(invoiceSaved));
        return factura;
    }


    public static ValueInvoice calcularDetalleFactura(final List<DeatailsInvoiceProduct> detailsProduct, final Double propina) {

        ValueInvoice valueInvoice = new ValueInvoice();

        Double subtotalCeroIva = 0.0;
        Double subtotalDoceIvaActual = 0.0;
        Double subtotalExeptoIva = 0.0;
        Double subtotalNoObjetoIva = 0.0;
        Double subtotal = 0.0;
        Double totalDescuento = 0.0;

        Double valorICE = 0.0;
        Double valorIRBPNR = 0.0;
        Double valorIvaVigente = 0.0;
        Double valorPropina = 0.0;
        Double valorTotal = 0.0;

        // Productos que gravan IVA 12%
        List<DeatailsInvoiceProduct> detailsGravaIVA = getDetailsInvoiceByIva(detailsProduct, QuestionEnum.SI);

        // Producto que no gravan IVA  (0%, No objeto iva, Exento iva)
        List<DeatailsInvoiceProduct> detailsNoGravaIVA = getDetailsInvoiceByIva(detailsProduct, QuestionEnum.NO);


        // Producto que gravan IVA
        if (detailsGravaIVA.size() > 0) {

            // Productos incluyen IVA
            subtotalDoceIvaActual = sumarSubtotalDetalle(detailsGravaIVA);

            // Obtener el iva vigente

            //valorIvaVigente = subtotalDoceIvaActual - (subtotalDoceIvaActual / ConstanteUtil.IVA_ACTUAL_DECIMAL);
            valorIvaVigente = (subtotalDoceIvaActual * ConstanteUtil.IVA_ACTUAL_PORCENTAJE) / 100;

            // Este el valor subtotal doce porciente, sin IVA
            //subtotalDoceIvaActual -= valorIvaVigente;
        }


        // Producto no gravan IVA
        if (detailsNoGravaIVA.size() > 0) {
            // Dividir los (0%, Exento, No Objeto Iva)

            //  Detalle con iva 0%
            List<DeatailsInvoiceProduct> listIvaCero = getDetailsInvoiceNoIva(detailsNoGravaIVA, TypePorcentajeIvaEnum.IVA_CERO);

            subtotalCeroIva = sumarSubtotalDetalle(listIvaCero);

            // Detalle con iva No Objeto
            List<DeatailsInvoiceProduct> listIvaNoObjeto = getDetailsInvoiceNoIva(detailsNoGravaIVA, TypePorcentajeIvaEnum.IVA_NO_OBJETO);

            subtotalNoObjetoIva = sumarSubtotalDetalle(listIvaNoObjeto);


            // Detalle con iva Exento
            List<DeatailsInvoiceProduct> listIvaExentoIva = getDetailsInvoiceNoIva(detailsNoGravaIVA, TypePorcentajeIvaEnum.IVA_EXENTO);

            subtotalExeptoIva = sumarSubtotalDetalle(listIvaExentoIva);
        }

        //
        valorIvaVigente += valorICE;

        //subtotal = subtotalDoceIvaActual + subtotalCeroIva + subtotalNoObjetoIva + subtotalExeptoIva;
        subtotal = subtotalDoceIvaActual + subtotalCeroIva + subtotalNoObjetoIva + subtotalExeptoIva;


        // Calcular el irbpnr
        valorIRBPNR = calcularIRBPNR(detailsProduct);

        //  EL subtotal corresponde a la base imponible, a ese valor le restamos el IRBPNR
        //subtotal -= valorIRBPNR;

        // Puede ser 10 10% del subtotal
        double propinaMaxima = (subtotal * 10) / 100;

        // La propina no puede superar a propina maxima
        if (propina > propinaMaxima) {
            throw new BadRequestException("La propina " + propina + " no puede superar el 10%  de" + subtotal);
        } else valorPropina = propina;


        // valorTotal = subtotal + valorICE + valorIRBPNR + valorIvaVigente + valorPropina;

        valorTotal = subtotal + valorICE + valorIRBPNR + valorIvaVigente + valorPropina;

        valueInvoice.setSubtIvaCero(subtotalCeroIva);

        valueInvoice.setSubtIvaActual(subtotalDoceIvaActual);

        valueInvoice.setSubtNoObjIva(subtotalNoObjetoIva);

        valueInvoice.setSubtExceptoIva(subtotalExeptoIva);

        valueInvoice.setSubtotal(subtotal);

        valueInvoice.setDescuento(totalDescuento);

        valueInvoice.setIce(valorICE);
        valueInvoice.setRbpnr(valorIRBPNR);

        valueInvoice.setIva(valorIvaVigente);

        valueInvoice.setPropina(valorPropina);

        valueInvoice.setTotal(valorTotal);

        return valueInvoice;

    }

    /**
     * Calcula el total del ibprn por cada linea de detalle de la factura
     *
     * @param detailsProduct
     * @return
     */
    public static Double calcularIRBPNR(List<DeatailsInvoiceProduct> detailsProduct) {

        return detailsProduct.stream().filter(item ->
                        item.getProduct().getIrbpnr().equalsIgnoreCase(QuestionEnum.SI.name()))
                .map(item -> (ConstanteUtil.VALOR_IVA_IRBPNR) * item.getAmount())
                .reduce(Double::sum).orElse(0.0);

    }

    private static List<DeatailsInvoiceProduct> getDetailsInvoiceByIva(List<DeatailsInvoiceProduct> detailsProduct, QuestionEnum questionEnum) {

        return detailsProduct
                .stream()
                .filter(item -> item.getProduct().getIva().equalsIgnoreCase(questionEnum.name()))
                .collect(Collectors.toList());
    }


    private static List<DeatailsInvoiceProduct> getDetailsInvoiceNoIva(List<DeatailsInvoiceProduct> detailsProducts, TypePorcentajeIvaEnum typeIvaEnum) {

        return detailsProducts
                .stream()
                .filter(item -> verificarImpuestoIvaProducto(item.getProduct(), typeIvaEnum))
                .collect(Collectors.toList());
    }

    private static boolean verificarImpuestoIvaProducto(Product product, TypePorcentajeIvaEnum typePorcentaje) {

        return product.getTaxProducts().stream().anyMatch(tax -> tax.getTaxValue().getCode().equalsIgnoreCase(typePorcentaje.getCode()));
    }

    /**
     * Calcular el total de un detalle de la factura
     *
     * @param detailProduct
     * @return
     */
    private static Double calcularSubtotalDetalle(DeatailsInvoiceProduct detailProduct) {

        return detailProduct.getUnitValue() * detailProduct.getAmount();
    }

    private static Double sumarSubtotalDetalle(List<DeatailsInvoiceProduct> detailProduct) {

        return detailProduct.stream().map(AdminInvoice::calcularSubtotalDetalle)
                .reduce(Double::sum).orElse(0.0);
    }

    /**
     * Asigna a cada detalle de la factura el producto correspondiente, para calcular los valores
     * de la factura
     *
     * @param products
     * @param deatailsProducts
     * @return
     */
    public static List<DeatailsInvoiceProduct> createDeatailsInvoiceProduct(List<Product> products, List<DeatailsInvoiceProductDTO> deatailsProducts) {

        List<DeatailsInvoiceProduct> deatailsInvoiceProducts = new ArrayList<>();

        for (DeatailsInvoiceProductDTO item : deatailsProducts) {

            DeatailsInvoiceProduct deatailsInvoiceProduct = new DeatailsInvoiceProduct();

            // Seteo los nuevos valores de los productos  ingresados por el cliente
            Product product = products
                    .stream()
                    .filter(pro -> pro.getIde().longValue() == item.getIdProducto()).findFirst().get();

            // Asignar el producto
            deatailsInvoiceProduct.setProduct(product);
            deatailsInvoiceProduct.setUnitValue(item.getUnitValue());
            deatailsInvoiceProduct.setAmount(item.getAmount());
            deatailsInvoiceProduct.setDescription((item.getDescription() != null && !item.getDescription().isEmpty()) ? item.getDescription() :
                    product.getName());
            deatailsInvoiceProduct.setSubtotal(deatailsInvoiceProduct.getAmount() * deatailsInvoiceProduct.getUnitValue());
            deatailsInvoiceProducts.add(deatailsInvoiceProduct);
        }
        return deatailsInvoiceProducts;
    }


    private static InfoTributaria createInfoTributaria(Invoice invoiceSaved, Sender sender) {

        InfoTributaria infoTributaria = new InfoTributaria();

        infoTributaria.setSecuencial(invoiceSaved.getNumberSecuencial());
        infoTributaria.setAmbiente(sender.getTypeEnvironment());
        infoTributaria.setTipoEmision(sender.getTypeEmission());
        infoTributaria.setRazonSocial(sender.getSocialReason());
        infoTributaria.setRuc(sender.getRuc());
        infoTributaria.setCodDoc(invoiceSaved.getTypeDocument());

        // Seteo el valor que debe ir en la xml
        if (sender.isRimpe())
            infoTributaria.setContribuyenteRimpe(ConstanteUtil.TEXT_DEFAULT_REGIMEN_RIMPE);

        if (sender.getRetentionAgent() != null &&
                !sender.getRetentionAgent().equals("null") && !sender.getRetentionAgent().isEmpty()) {
            infoTributaria.setAgenteRetencion(sender.getRetentionAgent());
        }

        infoTributaria.setClaveAcceso(invoiceSaved.getKeyAccess());

        if (sender.getCommercialName() != null && !sender.getCommercialName().equals("null") && !sender.getCommercialName().isEmpty())
            infoTributaria.setNombreComercial(sender.getCommercialName());

        return infoTributaria;
    }

    /**
     * Realizar una consulta inner para evitar realizar varias consultas
     *
     * @param invoiceSaved : Factura guardara
     * @return
     */
    public static InfoFactura createInfoFactura(Invoice invoiceSaved) {

        InfoFactura infoFactura = new InfoFactura();

        infoFactura.setFechaEmision(FunctionUtil.convertDateToString(invoiceSaved.getDateEmission()));

        // calcular el subsidrio,aun no se calcula
        infoFactura.setTotalSubsidio(BigDecimal.valueOf(0.00));

        infoFactura.setDirEstablecimiento(invoiceSaved.getEmissionPoint().getSubsidiary().getAddress());

        if (invoiceSaved.getGuiaRemission() != null && !invoiceSaved.getGuiaRemission().isEmpty())
            infoFactura.setGuiaRemision(invoiceSaved.getGuiaRemission());

        // Obtener el cliente de la factura Guardada
        Person person = invoiceSaved.getPerson();

        // factura se guardo como consumidor final
        if (person == null) {

            infoFactura.setTipoIdentificacionComprador(TypeIdentificationEnum.CONSUMIDOR_FINAL.getCode());
            infoFactura.setRazonSocialComprador(ConstanteUtil.TEXT_DEFAULT_CONSUMIDOR_FINAL);
            infoFactura.setIdentificacionComprador(ConstanteUtil.TEXT_DEFAULT_CODE_CONSUMIDOR_FINAL);
            // Factura se guardo con un cliente
        } else {

            infoFactura.setTipoIdentificacionComprador(person.getTipoIdentificacion());
            infoFactura.setRazonSocialComprador(person.getRazonSocial());
            infoFactura.setIdentificacionComprador(person.getNumeroIdentificacion());

            if (person.getAddress() != null && !person.getAddress().equals("null") && !person.getAddress().isEmpty())
                infoFactura.setDireccionComprador(person.getAddress());

        }


        ValueInvoice valueInvoice = invoiceSaved.getValueInvoice();

        infoFactura.setTotalSinImpuestos(BigDecimal.valueOf(valueInvoice.getSubtotal()));
        infoFactura.setTotalDescuento(BigDecimal.valueOf(valueInvoice.getDescuento()));
        infoFactura.setPropina(BigDecimal.valueOf(valueInvoice.getPropina()));

        infoFactura.setImporteTotal(null);
        infoFactura.setMoneda(ConstanteUtil.TEXT_DEFAULT_MODEDA);


        List<TotalImpuesto> totalImpuestos= createTotalImpuestos(invoiceSaved);

        // aqui se guardara los  impuesto
        TotalConImpuestos totalConImpuestos = new TotalConImpuestos();
        totalConImpuestos.setTotalImpuesto(totalImpuestos);

        infoFactura.setTotalConImpuestos(totalConImpuestos);

        // infoFactura.setPagos(null);

        Sender sender = invoiceSaved.getEmissionPoint().getSubsidiary().getSender();

        if (sender.getSpecialContributor() != null && !sender.getSpecialContributor().equals("null") && !sender.getSpecialContributor().isEmpty())
            infoFactura.setContribuyenteEspecial(sender.getSpecialContributor());

        if (sender.getAccountancy() != null && !sender.getAccountancy().name().equals("null"))
            infoFactura.setObligadoContabilidad(sender.getAccountancy().name());

        return infoFactura;

    }


    public static Detalles createDetalleFactura(Invoice invoiceSaved) {


        Detalles detalles = new Detalles();


        Detalle detalleItem = new Detalle();
        return null;
    }

    public static List<TotalImpuesto> createTotalImpuestos(Invoice invoiceSaved) {


        List<TotalImpuesto> totalImpuestos = new ArrayList<>();

        // Impuestos: ICE-IRPNR-ICE
        TotalImpuesto totalImpuestoICE = new TotalImpuesto();
        totalImpuestoICE.setCodigo(TypeTaxEnum.ICE.getCode());

        totalImpuestos.add(totalImpuestoICE);

        return totalImpuestos;
    }

}
