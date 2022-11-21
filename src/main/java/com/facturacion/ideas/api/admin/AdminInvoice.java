package com.facturacion.ideas.api.admin;

import com.facturacion.ideas.api.documents.InfoTributaria;
import com.facturacion.ideas.api.documents.factura.*;
import com.facturacion.ideas.api.dto.DeatailsInvoiceProductDTO;
import com.facturacion.ideas.api.dto.InvoiceNewDTO;
import com.facturacion.ideas.api.dto.PaymenNewtDTO;
import com.facturacion.ideas.api.entities.*;
import com.facturacion.ideas.api.enums.QuestionEnum;
import com.facturacion.ideas.api.enums.TypeIdentificationEnum;
import com.facturacion.ideas.api.enums.TypePorcentajeIvaEnum;
import com.facturacion.ideas.api.enums.TypeTaxEnum;
import com.facturacion.ideas.api.exeption.BadRequestException;
import com.facturacion.ideas.api.exeption.GenerateXMLExeption;
import com.facturacion.ideas.api.services.DocumentServiceImpl;
import com.facturacion.ideas.api.util.ArchivoUtils;
import com.facturacion.ideas.api.util.ConstanteUtil;
import com.facturacion.ideas.api.util.FunctionUtil;
import com.facturacion.ideas.api.util.PathDocuments;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AdminInvoice {

    private static Factura facturaGenerada = null;

    // Almacena la ruta hasta el directorio punto de emision
    private static String pathDirectory = null;

    private static final Logger LOGGER = LogManager.getLogger(AdminInvoice.class);

    public static String generatorFractureXML(Invoice invoiceXML, final InvoiceNewDTO invoiceNewDTO,
                                              List<Product> products) throws GenerateXMLExeption {

        // Reiniciar
        facturaGenerada = null;

        Factura factura = new Factura();
        factura.setInfoTributaria(createInfoTributaria(invoiceXML));
        factura.setDetalles(createDetalles(products, invoiceNewDTO.getDeatailsInvoiceProductDTOs()));
        factura.setInfoFactura(createInfoFactura(factura, invoiceXML, invoiceNewDTO));
        factura.setId("comprobante");
        factura.setVersion(ConstanteUtil.VERSION_XML);


        String pathNewFileGenerate = writeInvoiceXML(invoiceXML, factura);
        if (pathNewFileGenerate != null) {
            facturaGenerada = factura;
        } else facturaGenerada = null;

        return pathNewFileGenerate;
    }

    public static Factura getFacturaGenerada() {
        return facturaGenerada;
    }

    private static String writeInvoiceXML(Invoice invoiceXML, Factura factura) throws GenerateXMLExeption {

        EmissionPoint emissionPoint = invoiceXML.getEmissionPoint();
        Subsidiary subsidiary = emissionPoint.getSubsidiary();
        Sender sender = subsidiary.getSender();

        final String baseUrl = PathDocuments.PATH_BASE + sender.getRuc()
                + "/est_" + subsidiary.getCode() + "/emi_" + emissionPoint.getCodePoint();
        File file = new File(baseUrl);

        if (!file.exists()) {
            if (!file.mkdirs()) throw new GenerateXMLExeption("No se pudo crear el directorio para el comprobante");
        }

        // Esto me sirve cuando escriba el xml autorizado o no autorizado
        pathDirectory = file.getAbsolutePath();

        createDirectory();

        String pathArchivo = file.getAbsolutePath() + "/" + invoiceXML.getNumberAutorization() + ".xml";

        // Retorna la ruta de nuevo archivo xm generado
        return ArchivoUtils.realizarMarshall(factura, pathArchivo);
    }

    /**
     * Método se encarga de crear los directorios para los comprobante firmados, autorizados y no_autorizados
     * se deben crear solo si no existes
     */
    private static void createDirectory() {
        String[] directories = {"firmados", "autorizados", "no_autorizados"};

        for (String directory : directories) {
            File file = new File(pathDirectory.concat("/").concat(directory));
            if (!file.exists()) {
                if (file.mkdirs()) {
                    LOGGER.info("Directorio " + file.getAbsolutePath() + " creado con exito");
                } else LOGGER.info("Directorio " + file.getAbsolutePath() + " no puso se creado");
            }
        }

    }

    public static String getPathDirectory() {
        return pathDirectory;
    }

    private static InfoTributaria createInfoTributaria(Invoice invoiceSaved) {

        InfoTributaria infoTributaria = new InfoTributaria();

        EmissionPoint emissionPoint = invoiceSaved.getEmissionPoint();
        Subsidiary subsidiary = emissionPoint.getSubsidiary();
        Sender sender = subsidiary.getSender();


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

    public static Detalles createDetalles(List<Product> products, List<DeatailsInvoiceProductDTO> detailsProducts) {

        Detalles detalles = new Detalles();

        List<Detalle> detailsItemList = new ArrayList<>();

        detailsProducts.forEach(item -> {

            // Obtener el producto correspondiente a su idProducto del Detalle
            Product product = buscarProductoPorId(products, item.getIdProducto());

            // DetalleItem
            Detalle detalleItem = createDetalleItem(product, item);

            detailsItemList.add(detalleItem);

        });
        detalles.setDetalle(detailsItemList);

        return detalles;

    }

    private static Product buscarProductoPorId(List<Product> products, Long idBuscar) {

        return products
                .stream()
                .filter(pro -> pro.getIde().longValue() == idBuscar).findFirst()
                .orElse(null);

    }

    private static Detalle createDetalleItem(Product productDetalle,
                                             DeatailsInvoiceProductDTO itemDetailsProd) {


        Detalle detalleItem = new Detalle();

        detalleItem.setCodigoPrincipal(productDetalle.getCodePrivate());
        detalleItem.setDescripcion((itemDetailsProd.getDescription() != null && !itemDetailsProd.getDescription().isEmpty()) ? itemDetailsProd.getDescription() :
                productDetalle.getName());
        detalleItem.setCantidad(BigDecimal.valueOf(itemDetailsProd.getAmount()));
        detalleItem.setPrecioUnitario(BigDecimal.valueOf(itemDetailsProd.getUnitValue()));
        detalleItem.setDescuento(BigDecimal.ZERO.setScale(2));
        detalleItem.setPrecioTotalSinImpuesto(
                detalleItem.getPrecioUnitario().multiply(detalleItem.getCantidad()));


        // Informacion adicional
        List<ProductInformation> productInfoList = productDetalle.getProductInformations();

        if (productInfoList != null && productInfoList.size() > 0) {
            detalleItem.setDetallesAdicionales(createDetailsAdditionalProduct(productInfoList));
        }
        // Impuestos
        List<TaxProduct> productImpuestos = productDetalle.getTaxProducts();

        if (productImpuestos != null && productImpuestos.size() > 0) {
            detalleItem.setImpuestos(createDetalleImpuestos(productImpuestos, detalleItem, BigDecimal.valueOf(itemDetailsProd.getValorIce())));
        }
        return detalleItem;

    }

    public static InfoFactura createInfoFactura(Factura factura, Invoice invoiceXML, final InvoiceNewDTO invoiceNewDTO) {

        InfoFactura infoFactura = new InfoFactura();

        infoFactura.setFechaEmision(FunctionUtil.convertDateToStringSRI(invoiceXML.getDateEmission()));

        // calcular el subsidrio, esta pendiente
        BigDecimal totalSubsidio = BigDecimal.ZERO;

        // Esto es provicional, ya que no se utiliza mucho
        if (totalSubsidio.compareTo(BigDecimal.ONE) > 0) {
            infoFactura.setTotalSubsidio(BigDecimal.valueOf(0.00));
        }

        infoFactura.setDirEstablecimiento(invoiceXML.getEmissionPoint().getSubsidiary().getAddress());

        if (invoiceXML.getGuiaRemission() != null && !invoiceXML.getGuiaRemission().isEmpty()) {


            //  Longitud guia es 15 digitoa
            if (invoiceXML.getGuiaRemission().matches("[0-9]{15}")) {
                infoFactura.setGuiaRemision(invoiceXML.getGuiaRemission());
            } else
                throw new BadRequestException("El número para la guia de remision debe contener 15 dígitos, actualmente tiene " + invoiceXML.getGuiaRemission().length() + " dígitos");

        }


        // Obtener el cliente de la factura Guardada
        Person person = invoiceXML.getPerson();

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

        List<Detalle> detalles = factura.getDetalles().getDetalle();

        BigDecimal totalSinImpuestos = detalles.stream().map(Detalle::getPrecioTotalSinImpuesto)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        infoFactura.setTotalSinImpuestos(totalSinImpuestos);
        infoFactura.setTotalDescuento(BigDecimal.valueOf(invoiceNewDTO.getValueInvoiceNewDTO().getDescuento()).setScale(2));


        BigDecimal importeTotal = BigDecimal.ZERO;

        TotalConImpuestos totalConImpuestos = new TotalConImpuestos();

        List<TotalImpuesto> totalImpuestosList = new ArrayList<>();


        // Clasificar los detalles segun los impuestos ICE
        List<Impuesto> impuestosICE = getTipoImpuestoFactura(detalles, TypeTaxEnum.ICE);

        if (impuestosICE.size() > 0) {

            BigDecimal totalSumaImpuestoICE = impuestosICE.stream().map(Impuesto::getValor)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            // Agrupar el impuesto ICE por su codigoPorcentaje
            Map<String, List<Impuesto>> iceTipos = impuestosICE.stream().collect(Collectors.groupingBy(Impuesto::getCodigoPorcentaje));

            for (String key : iceTipos.keySet()) {

                List<Impuesto> impIce = iceTipos.get(key);

                BigDecimal baseImponible = impIce.stream().map(Impuesto::getBaseImponible)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                BigDecimal valorPorTipo = impIce.stream().map(Impuesto::getValor)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                TotalImpuesto totalImpuesto = new TotalImpuesto();
                totalImpuesto.setCodigo(TypeTaxEnum.ICE.getCode());
                totalImpuesto.setCodigoPorcentaje(key);
                totalImpuesto.setBaseImponible(baseImponible);
                // EN el ice no se agregar la tarifa
                totalImpuesto.setValor(valorPorTipo);
                totalImpuestosList.add(totalImpuesto);

            }

            importeTotal = importeTotal.add(totalSumaImpuestoICE);
        }

        // Clasificar los detalles segun los impuestos IVA
        List<Impuesto> impuestosIVA = getTipoImpuestoFactura(detalles, TypeTaxEnum.IVA);

        if (impuestosIVA.size() > 0) {

            BigDecimal totalSumaImpuestoIVA = impuestosIVA.stream().map(Impuesto::getValor)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            // Agrupar el impuesto ICE por su codigoPorcentaje
            Map<String, List<Impuesto>> ivaTipos = impuestosIVA.stream().collect(Collectors.groupingBy(Impuesto::getCodigoPorcentaje));

            for (String key : ivaTipos.keySet()) {

                List<Impuesto> impIva = ivaTipos.get(key);

                BigDecimal baseImponible = impIva.stream().map(Impuesto::getBaseImponible)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                BigDecimal valorPorTipo = impIva.stream().map(Impuesto::getValor)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                TotalImpuesto totalImpuesto = new TotalImpuesto();
                totalImpuesto.setCodigo(TypeTaxEnum.IVA.getCode());
                totalImpuesto.setCodigoPorcentaje(key);
                totalImpuesto.setBaseImponible(baseImponible);
                // Obtener el porcentaje del tipo de iva, mediante su codigo
                totalImpuesto.setTarifa(
                        BigDecimal.valueOf(TypePorcentajeIvaEnum.findTpePorcentajeIvaEnum(totalImpuesto.getCodigoPorcentaje()).getPorcentaje()
                        ).setScale(2));
                totalImpuesto.setValor(valorPorTipo.setScale(2));
                totalImpuestosList.add(totalImpuesto);
            }

            importeTotal = importeTotal.add(totalSumaImpuestoIVA);
        }

        totalConImpuestos.setTotalImpuesto(totalImpuestosList);

        infoFactura.setPropina(validarPropina(
                BigDecimal.valueOf(invoiceNewDTO.getValueInvoiceNewDTO().getPropina()), infoFactura.getTotalSinImpuestos()
        ).setScale(2));

        // Total de todo, precio sin impuestos + los impuestos
        importeTotal = importeTotal.add(infoFactura.getTotalSinImpuestos()).add(infoFactura.getPropina());

        /**
         * Validar que el total de la factura  corresponda a un cliente correcto:
         * Ejem: Si el cliente esta como consumirdor final, el total de la factua no pueder exceder a 200,
         * pueder seg == 200 pero no mayor
         */

        // Es un consumidor final qu excede en 200 el valor de factura
        if (importeTotal.compareTo(ConstanteUtil.VALOR_LIMITE_CONSUMIR_FINAL) > 0 && invoiceXML.getPerson() == null) {

            throw new BadRequestException("El total del comprobante $" + importeTotal.setScale(2) + " , excede el límite de $" + ConstanteUtil.VALOR_LIMITE_CONSUMIR_FINAL + " para " +
                    "clientes tipo Consumidor final, por favor, seleccione un cliente");
        }
        infoFactura.setTotalConImpuestos(totalConImpuestos);
        infoFactura.setImporteTotal(importeTotal.setScale(2));
        infoFactura.setMoneda(ConstanteUtil.TEXT_DEFAULT_MODEDA);

        List<PaymenNewtDTO> pagos = invoiceNewDTO.getPaymenNewtDTOS();

        if (pagos.size() > 0) {
            infoFactura.setPagos(createPagos(pagos));
        } else throw new BadRequestException("Debe seleccionar almenos una forma  de pago");

        Sender sender = invoiceXML.getEmissionPoint().getSubsidiary().getSender();

        if (sender.getSpecialContributor() != null && !sender.getSpecialContributor().equals("null") && !sender.getSpecialContributor().isEmpty())
            infoFactura.setContribuyenteEspecial(sender.getSpecialContributor());

        if (sender.getAccountancy() != null && !sender.getAccountancy().name().equals("null"))
            infoFactura.setObligadoContabilidad(sender.getAccountancy().name());

        return infoFactura;

    }

    private static BigDecimal validarPropina(BigDecimal propina, BigDecimal substotalSinImpuesto) {

        BigDecimal valorCalculado = (substotalSinImpuesto.multiply(BigDecimal.TEN)).divide(BigDecimal.valueOf(100));
        if (propina.compareTo(valorCalculado) > 0) {
            throw new BadRequestException("Valor de propina " + propina.setScale(2) + ", excede al 10% del total sin impuesto " +
                    substotalSinImpuesto + " (" + valorCalculado + " )");
        }

        return propina;
    }

    private static List<Impuesto> getTipoImpuestoFactura(List<Detalle> detalles, TypeTaxEnum typeTaxEnum) {

        List<Impuesto> impuestoList = new ArrayList<>();

        for (Detalle detalleItem : detalles) {

            Impuestos impuestos = detalleItem.getImpuestos();

            List<Impuesto> impuestoItem = impuestos.getImpuesto();

            for (Impuesto impuesto : impuestoItem) {

                if (impuesto.getCodigo().equalsIgnoreCase(typeTaxEnum.getCode())) {
                    impuestoList.add(impuesto);
                }
            }
        }

        return impuestoList;
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

    private static Pagos createPagos(List<PaymenNewtDTO> paymenNewtDTOS) {


        Pagos pagos = new Pagos();

        List<DetallePago> detallePagoList = new ArrayList<>();

        paymenNewtDTOS.forEach(item -> {
            DetallePago detallePago = new DetallePago();
            detallePago.setFormaPago(item.getCode());
            detallePago.setTotal(item.getTotal());

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

    private static Impuestos createDetalleImpuestos(List<TaxProduct> productImpuestos, Detalle detalleItem, BigDecimal valueICE) {

        // Elemento ROOT
        Impuestos impuestos = new Impuestos();

        // Lista de impuestos
        List<Impuesto> impuestoList = new ArrayList<>();

        TaxValue impuestoICE = getTipoImpuesto(productImpuestos, TypeTaxEnum.ICE);

        if (impuestoICE != null) {
            Impuesto iceImp = createImpuestosItem(impuestoICE, detalleItem);
            iceImp.setValor(valueICE);
            impuestoList.add(iceImp);
        }


        TaxValue impuestoIVA = getTipoImpuesto(productImpuestos, TypeTaxEnum.IVA);
        if (impuestoIVA != null) {
            Impuesto ivaImp = createImpuestosItem(impuestoIVA, detalleItem);
            ivaImp.setBaseImponible(detalleItem.getPrecioTotalSinImpuesto().add(valueICE));
            BigDecimal calculoIva = (ivaImp.getBaseImponible().multiply(ivaImp.getTarifa())).divide(BigDecimal.valueOf(100));

            ivaImp.setValor(calculoIva.setScale(2));
            impuestoList.add(ivaImp);
        }
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

    public static List<Long> getIdsProductDetails(List<DeatailsInvoiceProductDTO> deatailsInvoiceProductDTOS) {

        return deatailsInvoiceProductDTOS.stream()
                .map(DeatailsInvoiceProductDTO::getIdProducto)
                .collect(Collectors.toList());
    }

    public static TaxValue getTipoImpuesto(List<TaxProduct> productImpuestos, TypeTaxEnum typeTaxEnum) {

        return productImpuestos.stream()
                .map(TaxProduct::getTaxValue)
                // EL ide del Tax es el codigo del impuesto mismo
                .filter(item -> (item.getTax().getIde() + "").equalsIgnoreCase(typeTaxEnum.getCode()))
                .findFirst().orElse(null);

    }


    private static Impuesto createImpuestosItem(TaxValue impuesto, Detalle item) {

        Impuesto impuestoItem = new Impuesto();
        impuestoItem.setCodigo(impuesto.getTax().getIde() + "");
        impuestoItem.setCodigoPorcentaje(impuesto.getCode());

        if ((impuesto.getTax().getIde() + "").equalsIgnoreCase(TypeTaxEnum.ICE.getCode())) {
            impuestoItem.setTarifa(BigDecimal.ZERO);
        } else if ((impuesto.getTax().getIde() + "").equalsIgnoreCase(TypeTaxEnum.IVA.getCode())) {
            impuestoItem.setTarifa(BigDecimal.valueOf(impuesto.getPorcentage()).setScale(2));
        }

        impuestoItem.setBaseImponible(item.getPrecioTotalSinImpuesto());

        return impuestoItem;
    }

}
