package com.facturacion.ideas.api.admin;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.facturacion.ideas.api.documents.InfoTributaria;
import com.facturacion.ideas.api.documents.factura.Factura;
import com.facturacion.ideas.api.documents.factura.InfoFactura;
import com.facturacion.ideas.api.dto.DeatailsInvoiceProductDTO;
import com.facturacion.ideas.api.dto.InvoiceNewDTO;
import com.facturacion.ideas.api.dto.ValueInvoiceNewDTO;
import com.facturacion.ideas.api.entities.*;
import com.facturacion.ideas.api.enums.*;
import com.facturacion.ideas.api.exeption.BadRequestException;
import com.facturacion.ideas.api.exeption.KeyAccessException;
import com.facturacion.ideas.api.exeption.NotFoundException;
import com.facturacion.ideas.api.util.ConstanteUtil;
import com.facturacion.ideas.api.util.FunctionUtil;

public class AdminDocument {

    public static String generateKeyAcces(InvoiceNewDTO invoiceNewDTO, Sender sender, Subsidiary subsidiary,
                                          EmissionPoint emissionPoint) {

        // * 1) Fecha Actual
        String today = new SimpleDateFormat(ConstanteUtil.DATE_FORMAT_KEY_ACCESS)
                .format(Calendar.getInstance().getTime());

        // Tipo Comprobante
        TypeDocumentEnum typeDocument = TypeDocumentEnum.getTypeDocumentEnum(invoiceNewDTO.getTypeDocument());

        if (typeDocument == null) {
            throw new NotFoundException("Tipo Documento " + invoiceNewDTO.getTypeDocument() + " no es valido");
        }

        // * 2) Codigo del tipo documento
        String codeTypeDocument = typeDocument.getCode();

        // * 3) Ruc del emisor
        String ruc = sender.getRuc();

        // * 4) Tipo Ambiente
        String codeTypeEnviroment = sender.getTypeEnvironment();

        // * 5 Serie : conformada por codigo de establecimiento y codigo punto emision
        String serie = subsidiary.getCode() + emissionPoint.getCodePoint();

        // * 6 Numero de comprobante secuencial
        String squentialNumberDocument = invoiceNewDTO.getNumberSecuencial();


        // * 7 Codigo numerico
        String codeNumeric = ConstanteUtil.CODE_NUMERIC_KEY_ACCESS;

        // * 8 Tipo emission
        String codeTypeEmission = invoiceNewDTO.getTypoEmision();
        // invoiceNewDTO.setTypoEmision(squentialNumberDocument);

        String keyAccess = today + codeTypeDocument + ruc + codeTypeEnviroment + serie + squentialNumberDocument
                + codeNumeric + codeTypeEmission;
        return generateCheckDigit(keyAccess);

    }

    /**
     * Geneara el digito verificador aplicando el Modulo 11.
     *
     * @param keyAccess : clave de acceso de 48 digitos
     * @return
     */
    public static String generateCheckDigit(String keyAccess) {

        Pattern pat = Pattern.compile("[0-9]{48}");
        Matcher mat = pat.matcher(keyAccess);

        if (!mat.matches()) {
            throw new KeyAccessException("La clave de acceso debe tener solo 48 digitos: " + keyAccess.length());
        }

        // Convertir en array los digitos
        String digits[] = keyAccess.split("");

        int suma = 0;
        int factor = 7;
        for (int index = 0; index < digits.length; index++) {
            suma += Integer.parseInt(digits[index]) * 7;
            factor -= 1;

            if (factor == 1) {
                factor = 7;
            }

        }

        int checkDigit = (suma % 11);

        checkDigit = 11 - checkDigit;

        if (checkDigit == 10)
            checkDigit = 1;

        if (checkDigit == 11)
            checkDigit = 0;

        return keyAccess + checkDigit;
    }

    /**
     * Genera el siguiente numero de documento
     *
     * @param currentNumber : el numero actual del documento
     * @return
     */
    public static String nextSquentialNumberDocument(int currentNumber) {

        // Numero de digitos que permitira saber cuantos ceros(0) se debe eliminar
        int amountDigits = (currentNumber + "").length();

        String numberInvoiceDefault = "000000000".trim();

        // Cantidad de espacios a recortar
        int endLong = numberInvoiceDefault.length() - amountDigits;

        // Quitar cantidad segun el numero de digitos
        numberInvoiceDefault = numberInvoiceDefault.substring(0, endLong) + currentNumber;

        return numberInvoiceDefault;

    }

    /**
     * Crear una Factura
     *
     * @param invoiceSaved : La informacion de la factura guardada
     * @return : La factura que representara el XML
     */
    public static Factura generarFactura(Invoice invoiceSaved) {

        Factura factura = new Factura();

        return null;


    }

    private InfoTributaria createInfoTributaria(Invoice invoiceSaved, Sender sender) {

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

        // calcular
        // infoFactura.setTotalSubsidio(null);

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

        // infoFactura.setTotalSinImpuestos(null);
        // infoFactura.setTotalDescuento(null);
        // infoFactura.setPropina(null);

        // infoFactura.setImporteTotal(null);
        infoFactura.setMoneda(ConstanteUtil.TEXT_DEFAULT_MODEDA);
        // infoFactura.setTotalConImpuestos(null);

        // infoFactura.setPagos(null);

        Sender sender = invoiceSaved.getEmissionPoint().getSubsidiary().getSender();

        if (sender.getSpecialContributor() != null && !sender.getSpecialContributor().equals("null") && !sender.getSpecialContributor().isEmpty())
            infoFactura.setContribuyenteEspecial(sender.getSpecialContributor());

        if (sender.getAccountancy() != null && !sender.getAccountancy().name().equals("null"))
            infoFactura.setObligadoContabilidad(sender.getAccountancy().name());

        return infoFactura;

    }

    public static ValueInvoice calcularDetalleFactura(List<DeatailsInvoiceProduct> detailsProduct, Double propina) {

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
        List<DeatailsInvoiceProduct> detailsGravaIVA = detailsProduct
                .stream()
                .filter(item -> item.getProduct().getIva().equalsIgnoreCase(QuestionEnum.SI.name()))
                .collect(Collectors.toList());


        // Producto que no gravan IVA  (0%, No objeto iva, Exento iva)
        List<DeatailsInvoiceProduct> detailsNoGravaIVA = detailsProduct
                .stream()
                .filter(item -> item.getProduct().getIva().equalsIgnoreCase(QuestionEnum.NO.name()))
                .collect(Collectors.toList());

        // Indica que todos los producto del detalle factura Gravan IVA
        if (detailsGravaIVA.size() == detailsProduct.size() && detailsNoGravaIVA.isEmpty()) {

            // Aqui la suma ya incluye iva
            subtotalDoceIvaActual = detailsGravaIVA.stream().map(item -> (item.getProduct().getUnitValue() * item.getAmount()))
                    .reduce(Double::sum).get();

            // Obtener el iva vigente
            valorIvaVigente = subtotalDoceIvaActual / ConstanteUtil.IVA_ACTUAL_DECIMAL;

            // Este el valor subtotal doce porciente, sin IVA
            subtotalDoceIvaActual -= valorIvaVigente;

        } else {
            // Dividir los (0%, Exento, No Objeto Iva)

            //  Detalle con iva 0%
            List<DeatailsInvoiceProduct> listIvaCero = detailsNoGravaIVA
                    .stream()
                    .filter(item -> verificarImpuestoIvaProducto(item.getProduct(), TypePorcentajeIvaEnum.IVA_CERO))
                    .collect(Collectors.toList());


            subtotalCeroIva = listIvaCero.stream().map(item -> (item.getProduct().getUnitValue() * item.getAmount()))
                    .reduce(Double::sum).get();

            // Detalle con iva No Objeto
            List<DeatailsInvoiceProduct> listIvaNoObjeto = detailsNoGravaIVA
                    .stream()
                    .filter(item -> verificarImpuestoIvaProducto(item.getProduct(), TypePorcentajeIvaEnum.IVA_NO_OBJETO))
                    .collect(Collectors.toList());

            subtotalNoObjetoIva = listIvaNoObjeto.stream().map(item -> (item.getProduct().getUnitValue() * item.getAmount()))
                    .reduce(Double::sum).get();


            // Detalle con iva Exento
            List<DeatailsInvoiceProduct> listIvaExentoIva = detailsNoGravaIVA
                    .stream()
                    .filter(item -> verificarImpuestoIvaProducto(item.getProduct(), TypePorcentajeIvaEnum.IVA_EXENTO))
                    .collect(Collectors.toList());

            subtotalExeptoIva = listIvaExentoIva.stream().map(item -> (item.getProduct().getUnitValue() * item.getAmount()))
                    .reduce(Double::sum).get();
        }


        //
        valorIvaVigente += valorICE;

        subtotal = subtotalDoceIvaActual + subtotalCeroIva + subtotalNoObjetoIva + subtotalExeptoIva;

        // La propina no puede superar el 10% del subtotal
        if ((subtotal * 10) / 100 > propina) {
            throw new BadRequestException("La propina " + propina + " no puede superar el 10% de " + subtotal);
        } else valorPropina = propina;



        valorTotal = subtotal +  valorICE +  valorIRBPNR + valorIvaVigente +  valorPropina;


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

    private static boolean verificarImpuestoIvaProducto(Product product, TypePorcentajeIvaEnum typePorcentaje) {

        return product.getTaxProducts().stream().anyMatch(tax -> tax.getTaxValue().getCode().equalsIgnoreCase(typePorcentaje.getCode()));
    }

    /**
     * Verificar que si un producto No graban iva, 0%, Exento, No Objeto
     *
     * @param taxValue
     * @return
     */
    private static boolean verificarProductoNoIva(TaxValue taxValue) {
        return taxValue.getCode().equalsIgnoreCase(TypePorcentajeIvaEnum.IVA_CERO.name())
                || taxValue.getCode().equalsIgnoreCase(TypePorcentajeIvaEnum.IVA_NO_OBJETO.name())
                || taxValue.getCode().equalsIgnoreCase(TypePorcentajeIvaEnum.IVA_EXENTO.name());


    }

    /**
     * Calcular el total de un detalle de la factura
     *
     * @param detailProduct
     * @return
     */
    private static Double calcularTotalDetalle(DeatailsInvoiceProduct detailProduct) {

        return detailProduct.getProduct().getUnitValue() * detailProduct.getAmount();

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

            Product product = products
                    .stream()
                    .filter(pro -> pro.getIde().longValue() == item.getIdProducto()).findFirst().get();
            // Asignar el producto
            deatailsInvoiceProduct.setProduct(product);

            deatailsInvoiceProduct.setAmount(item.getAmount());
            deatailsInvoiceProduct.setSubtotal(item.getSubtotal());
            // Correspode al nombre del servicio o producto, pero si lo edita, lo guardo
            deatailsInvoiceProduct.setDescription(item.getDescription().isEmpty()? product.getName() : item.getDescription() );
            deatailsInvoiceProducts.add(deatailsInvoiceProduct);
        }
        return deatailsInvoiceProducts;
    }


}
