package com.facturacion.ideas.api.admin;

import com.facturacion.ideas.api.documents.InfoTributaria;
import com.facturacion.ideas.api.documents.factura.*;
import com.facturacion.ideas.api.dto.DeatailsInvoiceProductDTO;
import com.facturacion.ideas.api.dto.PaymenNewtDTO;
import com.facturacion.ideas.api.entities.*;
import com.facturacion.ideas.api.enums.*;
import com.facturacion.ideas.api.exeption.BadRequestException;
import com.facturacion.ideas.api.exeption.GenerateXMLExeption;
import com.facturacion.ideas.api.util.ArchivoUtils;
import com.facturacion.ideas.api.util.ConstanteUtil;
import com.facturacion.ideas.api.util.FunctionUtil;
import com.facturacion.ideas.api.util.PathDocuments;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdminInvoice {

    public static boolean guardarfacturaXML(Invoice invoiceSaved, List<PaymenNewtDTO> paymenNewtDTOS) {

        EmissionPoint emissionPoint = invoiceSaved.getEmissionPoint();
        Subsidiary subsidiary = emissionPoint.getSubsidiary();
        Sender sender = subsidiary.getSender();

        try {
            Factura factura = AdminInvoice.createFacturaXML(invoiceSaved, sender, paymenNewtDTOS);

            String baseUrl = PathDocuments.PATH_BASE + "/" + sender.getRuc()
                    + "/est_" + subsidiary.getCode() + "/emi_" + emissionPoint.getCodePoint();
            File file = new File(baseUrl);

            if (!file.exists()) {
                file.mkdirs();
            }

            factura.setId("comprobante");
            factura.setVersion(ConstanteUtil.VERSION_XML);

            String pathArchivo = file.getAbsolutePath() + "/" + invoiceSaved.getTypeDocument() + "_" + invoiceSaved.getNumberAutorization() + "_" + FunctionUtil.convertDateToString(invoiceSaved.getDateEmission()) + ".xml";
            return ArchivoUtils.realizarMarshall(factura, pathArchivo);

        } catch (Exception e) {
            throw new GenerateXMLExeption(e.getMessage());
        }

    }


    /**
     * Crear una Factura
     *
     * @param invoiceSaved : La informacion de la factura guardada
     * @return : La factura que representara el XML
     */
    public static Factura createFacturaXML(Invoice invoiceSaved, Sender sender, List<PaymenNewtDTO> paymenNewtDTOS) {

        Factura factura = new Factura();

        factura.setInfoTributaria(createInfoTributaria(invoiceSaved, sender));
        factura.setInfoFactura(createInfoFactura(invoiceSaved, paymenNewtDTOS));
        factura.setDetalles(createDetalleFactura(invoiceSaved));
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
        // Subtotal sin impuesto
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

        EmissionPoint emissionPoint = invoiceSaved.getEmissionPoint();

        Subsidiary subsidiary = emissionPoint.getSubsidiary();


        infoTributaria.setSecuencial(invoiceSaved.getNumberSecuencial());
        infoTributaria.setAmbiente(sender.getTypeEnvironment());
        infoTributaria.setTipoEmision(sender.getTypeEmission());
        infoTributaria.setRazonSocial(sender.getSocialReason());
        infoTributaria.setRuc(sender.getRuc());
        infoTributaria.setCodDoc(invoiceSaved.getTypeDocument());
        infoTributaria.setPtoEmi(emissionPoint.getCodePoint());
        infoTributaria.setEstab(subsidiary.getCode());
        infoTributaria.setDirMatriz(sender.getMatrixAddress());

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
    public static InfoFactura createInfoFactura(Invoice invoiceSaved, List<PaymenNewtDTO> paymenNewtDTOS) {

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

        List<TotalImpuesto> totalImpuestos = createTotalImpuestos(invoiceSaved);

        // aqui se guardara los  impuesto
        TotalConImpuestos totalConImpuestos = new TotalConImpuestos();
        totalConImpuestos.setTotalImpuesto(totalImpuestos);

        infoFactura.setTotalConImpuestos(totalConImpuestos);

        if (paymenNewtDTOS != null && paymenNewtDTOS.size() > 0) {
            infoFactura.setPagos(createPagos(paymenNewtDTOS));
        } else throw new BadRequestException("Debe seleccionar almenos una forma  de pago");


        Sender sender = invoiceSaved.getEmissionPoint().getSubsidiary().getSender();

        if (sender.getSpecialContributor() != null && !sender.getSpecialContributor().equals("null") && !sender.getSpecialContributor().isEmpty())
            infoFactura.setContribuyenteEspecial(sender.getSpecialContributor());

        if (sender.getAccountancy() != null && !sender.getAccountancy().name().equals("null"))
            infoFactura.setObligadoContabilidad(sender.getAccountancy().name());

        return infoFactura;

    }

    private static Pagos createPagos(List<PaymenNewtDTO> paymenNewtDTOS) {


        Pagos pagos = new Pagos();

        List<DetallePago> detallePagoList = new ArrayList<>();

        paymenNewtDTOS.forEach(item -> {
            DetallePago detallePago = new DetallePago();
            detallePago.setFormaPago(item.getCode());
            //detallePago.setTotal(item.getTotal());
            detallePago.setTotal(BigDecimal.valueOf(200)); // correguir esto

            // Estos no son olbigatorio
            if (item.getPlazo() != null && item.getPlazo().matches("[0-9]+")) {
                detallePago.setPlazo(item.getPlazo());
            }
            // Estos no son olbigatorio
            if (item.getUnidadTiempo() != null && !item.getPlazo().isEmpty()) {
                detallePago.setUnidadTiempo(item.getUnidadTiempo());
            }
            detallePagoList.add(detallePago);
        });

        pagos.setPago(detallePagoList);

        return pagos;
    }


    public static Detalles createDetalleFactura(Invoice invoiceSaved) {

        // Contenedor
        Detalles detallesRoot = new Detalles();

        List<Detalle> detalleList = new ArrayList<>();

        // Aqui el valor del precio del producto, fue el que llego desde el Fronted
        List<DeatailsInvoiceProduct> detailsProducto = invoiceSaved.getDeatailsInvoiceProducts();

        for (DeatailsInvoiceProduct item : detailsProducto) {

            Detalle detalleItem = createDetalleFactura(item);
            detalleList.add(detalleItem);
        }
        detallesRoot.setDetalle(detalleList);

        return detallesRoot;
    }

    private static Detalle createDetalleFactura(DeatailsInvoiceProduct itemDetails) {

        Detalle detalleItem = new Detalle();

        Product productDetalle = itemDetails.getProduct();

        detalleItem.setCodigoPrincipal(productDetalle.getCodePrivate());
        detalleItem.setDescripcion(productDetalle.getName());
        detalleItem.setCantidad(BigDecimal.valueOf(itemDetails.getAmount()));
        detalleItem.setPrecioUnitario(BigDecimal.valueOf(productDetalle.getUnitValue()));
        detalleItem.setPrecioTotalSinImpuesto(BigDecimal.valueOf(itemDetails.getSubtotal()));

        // Detalles Adicionales del Producto
        List<ProductInformation> infoAdicionalList = productDetalle.getProductInformations();

        if (infoAdicionalList != null && infoAdicionalList.size() > 0) {
            detalleItem.setDetallesAdicionales(createDetailsAdditionalProduct(infoAdicionalList));
        }

        detalleItem.setImpuestos(createDetalleImpuestos(productDetalle));

        return detalleItem;

    }

    private static Impuestos createDetalleImpuestos(Product product) {

        // Elemento ROOT
        Impuestos impuestos = new Impuestos();

        // Lista de impuestos
        List<Impuesto> impuestoList = new ArrayList<>();

        List<TaxProduct> productImpuestos = product.getTaxProducts();

        BigDecimal valorIce = BigDecimal.ZERO;

        BigDecimal valorIva = BigDecimal.ZERO;

        TaxValue impuestoICE = productImpuestos.stream()
                .map(TaxProduct::getTaxValue)
                .filter(item -> item.getTax().getTypeTax().equalsIgnoreCase(TypeTaxEnum.ICE.getCode()))
                .findFirst().orElse(null);

        if (impuestoICE != null) {

            Impuesto iceImp = new Impuesto();

            iceImp.setCodigo(impuestoICE.getTax().getTypeTax());
            iceImp.setCodigoPorcentaje(impuestoICE.getCode());
            iceImp.setTarifa(BigDecimal.ZERO);
            iceImp.setBaseImponible(BigDecimal.valueOf(product.getUnitValue()));

            // Esto se debe cambiar
            valorIce = BigDecimal.valueOf(0);

            iceImp.setValor(valorIce);

            impuestoList.add(iceImp);
        }


        TaxValue impuestoIVA = productImpuestos.stream()
                .map(TaxProduct::getTaxValue)
                .filter(item -> item.getTax().getTypeTax().equalsIgnoreCase(TypeTaxEnum.IVA.getCode()))
                .findFirst().orElse(null);

        if (impuestoIVA != null) {


            Impuesto ivaImp = new Impuesto();

            ivaImp.setCodigo(impuestoIVA.getTax().getTypeTax());
            ivaImp.setCodigoPorcentaje(impuestoIVA.getCode());
            ivaImp.setTarifa(BigDecimal.valueOf(impuestoIVA.getPorcentage()));

            // Al precio del producto se le suma el valor del ICE

            valorIva = BigDecimal.valueOf(product.getUnitValue()).add(BigDecimal.valueOf(product.getUnitValue()));
            ivaImp.setBaseImponible(valorIva);

            impuestoList.add(ivaImp);
        }
        // iVA
        impuestos.setImpuesto(impuestoList);

        return impuestos;
    }

    private static DetallesAdicionales createDetailsAdditionalProduct(List<ProductInformation> infoAdicionalList) {

        // Informacion adicional ROOT
        DetallesAdicionales detallesAdicionales = new DetallesAdicionales();

        List<DetAdicional> detAdicionalsList = new ArrayList<>();
        for (ProductInformation proInformacion : infoAdicionalList) {

            DetAdicional detAdicional = new DetAdicional();
            detAdicional.setNombre(proInformacion.getAttribute());
            detAdicional.setValor(proInformacion.getValue());
            detAdicionalsList.add(detAdicional);
        }

        // Asigno al elemento root de detalles adicional
        detallesAdicionales.setDetAdicional(detAdicionalsList);

        return detallesAdicionales;
    }

    public static List<TotalImpuesto> createTotalImpuestos(Invoice invoiceSaved) {


        List<TotalImpuesto> totalImpuestos = new ArrayList<>();

        //invoiceSaved.getDeatailsInvoiceProducts().get(0).


        // Impuestos: ICE-IRPNR-ICE


        TotalImpuesto totalImpuestoICE = new TotalImpuesto();
        totalImpuestoICE.setCodigo(TypeTaxEnum.ICE.getCode());
        totalImpuestoICE.setCodigoPorcentaje("2");

        // SOlo para  el iva se le pasa la tarifa
        totalImpuestoICE.setTarifa(BigDecimal.valueOf(12));

        totalImpuestoICE.setValor(BigDecimal.valueOf(222));

        totalImpuestos.add(totalImpuestoICE);

        return totalImpuestos;
    }

}
