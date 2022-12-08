package com.facturacion.ideas.api.services;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

import com.facturacion.ideas.api.admin.AdminInvoice;
import com.facturacion.ideas.api.documents.factura.Factura;
import com.facturacion.ideas.api.dto.*;
import com.facturacion.ideas.api.entities.*;
import com.facturacion.ideas.api.enums.*;
import com.facturacion.ideas.api.exeption.*;
import com.facturacion.ideas.api.repositories.*;
import com.facturacion.ideas.api.sri.cliente.ClientSRI;
import com.facturacion.ideas.api.sri.ws.autorizacion.Autorizacion;
import com.facturacion.ideas.api.sri.ws.autorizacion.RespuestaComprobante;
import com.facturacion.ideas.api.sri.ws.recepcion.RespuestaSolicitud;
import com.facturacion.ideas.api.util.SignatureDocumentXML;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.facturacion.ideas.api.admin.AdminDocument;
import com.facturacion.ideas.api.mapper.IDocumentMapper;
import com.facturacion.ideas.api.util.ConstanteUtil;

@Service
public class DocumentServiceImpl implements IDocumentService {

    private static final Logger LOGGER = LogManager.getLogger(DocumentServiceImpl.class);

    @Autowired
    private IInvoiceRepository invoiceRepository;

    @Autowired
    private IPersonRepository personRepository;

    @Autowired
    private IEmissionPointRepository emissionPointRepository;

    @Autowired
    private IInvoiceNumberRepository invoiceNumberRepository;

    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private IDocumentMapper documentMapper;

    @Autowired
    private ClientSRI clientSRI;

    @Autowired
    private SignatureDocumentXML signatureDocumentXML;

    private String pathNewInvoiceXML = null;

    private String pathFileSigned = null;

    private Factura facturaGenerada = null;


    @Autowired
    private IEncryptionService encryptionService;

    @Override
    @Transactional
    public ResponseWebServiceDTO saveInvoice(final InvoiceNewDTO invoiceNewDTO) {

        LOGGER.debug("Dentro de service saveInvoice");

        try {
            Invoice invoiceXML = new Invoice();

            EmissionPoint emissionPoint = emissionPointRepository.fechtSubsidiaryToSender(invoiceNewDTO.getIdEmissionPoint())
                    .orElseThrow(() -> new NotFoundException("Punto Emisión id " + invoiceNewDTO.getIdEmissionPoint()
                            + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION));

            // Consultar los productos del detalle
            List<Product> products = searchProductsDetailsByIds(
                    AdminInvoice.getIdsProductDetails(invoiceNewDTO.getDeatailsInvoiceProductDTOs()));

            // Establecimiento del punto emision
            Subsidiary subsidiary = emissionPoint.getSubsidiary();

            // Emisor punto emision
            Sender sender = subsidiary.getSender();

            invoiceXML.setEmissionPoint(emissionPoint);

            // Cliente
            if (invoiceNewDTO.getIdPerson() != null) {
                // Buscar Persona(cliente o Transportista) y asignar a al Factura
                invoiceXML.setPerson(personRepository.findById(invoiceNewDTO.getIdPerson()).orElseThrow(() ->
                        new NotFoundException("Cliente con ide " + invoiceNewDTO.getIdPerson() + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION)));

                // Asgino null, que representa a consumidor final
            } else
                invoiceXML.setPerson(null);


            // Numero actual del documento + 1, segun su tipo de documento
            // IMPORTANTE: este numero se guardara en el factura valores
            final int numberSecuncial = (getCurrentSequentialNumberByEmissionPoint(invoiceNewDTO.getTypeDocument(),
                    emissionPoint.getIde())) + 1;

            // Numero secuencia del Documento
            invoiceXML.setNumberSecuencial(AdminDocument.nextSquentialNumberDocument(numberSecuncial));
            invoiceXML.setTypeDocument(TypeDocumentEnum.getTypeDocumentEnum(invoiceNewDTO.getTypeDocument()));
            // Tipo de emision
            invoiceXML.setTypoEmision(sender.getTypeEmission());

            String keyAccess = AdminDocument.generateKeyAcces(invoiceXML);

            LOGGER.info("Clave acceso documento: " + keyAccess + " Longitud : " + keyAccess.length());

            invoiceXML.setKeyAccess(keyAccess);
            invoiceXML.setNumberAutorization(keyAccess);
            invoiceXML.setDateEmission(new Date());
            invoiceXML.setGuiaRemission(invoiceNewDTO.getRemissionGuideNumber());

            pathNewInvoiceXML = AdminInvoice.generatorFractureXML(invoiceXML, invoiceNewDTO, products);

            facturaGenerada = AdminInvoice.getFacturaGenerada();

            // Obtener datos de la firma electronica

            final String claveDescriptada = encryptionService.deEncrypt(sender.getPasswordCerticate());

            // Si el documento no puede ser firmado, se lo elimina
            pathFileSigned = signatureDocumentXML.setDataDocumentXML(facturaGenerada.getInfoTributaria().getRuc()
                    , pathNewInvoiceXML,
                    claveDescriptada, sender.getNameCerticate(), facturaGenerada.getInfoTributaria().getClaveAcceso());


            //consumeWebService(invoiceXML, numberSecuncial);
            return consumeWebService(invoiceXML, numberSecuncial);

        } catch (GenerateXMLExeption e) { // Exception al generar el xml e guardarlo  y crear el directorio de firmados
            LOGGER.error(e.getMessage(), e);
            throw new GenerateXMLExeption(e.getMessage());

        } catch (SignatureException e) { // Excepcion al  firmar el documento
            LOGGER.error(e.getMessage(), e);

            /**
             * Si el documento no pudo ser firmado, se lo elimina
             */
            AdminDocument.deleteDocument(pathNewInvoiceXML);
            throw new SignatureException(e.getMessage());

        } catch (ConsumeWebServiceException e) { // Excepcion al consumir web service
            LOGGER.error(e.getMessage(), e);
            throw new ConsumeWebServiceException(e.getMessage());
        } catch (EncryptedException e) { // mensjae viene desde el metodo
            LOGGER.error("Error guardar factura, desecyptar passwor", e);
            throw new EncryptedException(e.getMessage());
        } catch (DataAccessException e) {
            LOGGER.error("Error guardar factura", e);
            throw new NotDataAccessException("Error al guardar la factura");

        }

        /* catch (NotDataAccessException e) { // Excepcion al actualizar el secuencial de la factura
            throw new NotDataAccessException(e.getMessage());
        }*/

    }

    @Override
    @Transactional(readOnly = true)
    public ValueInvoiceNewDTO calcularValoresDocumento(List<DeatailsInvoiceProductDTO> detailsDocument) {


        try {

            ValueInvoiceNewDTO valuesDocuments = new ValueInvoiceNewDTO();

            BigDecimal subtotalCero = BigDecimal.ZERO;
            BigDecimal subtotalDoce = BigDecimal.ZERO;
            BigDecimal subtotalNoObjeto = BigDecimal.ZERO;
            BigDecimal subtotalExcepto = BigDecimal.ZERO;
            BigDecimal descuento = BigDecimal.ZERO;
            BigDecimal ice = BigDecimal.ZERO;
            BigDecimal irbprn = BigDecimal.ZERO;
            BigDecimal propina = BigDecimal.ZERO;
            BigDecimal subTotal = BigDecimal.ZERO;
            BigDecimal iva = BigDecimal.ZERO;
            BigDecimal total = BigDecimal.ZERO;


            for (DeatailsInvoiceProductDTO item : detailsDocument) {

                // Trae todo los datos del producto y sus relaciones
                Product product = productRepository.fetchTaxValueAndInfoDetailsById(item.getIdProducto()).orElseThrow(
                        () -> new NotFoundException(String.format("Producto %s %s", item.getIdProducto(), ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION))
                );

                List<TaxProduct> impuestos = product.getTaxProducts();

                for (TaxProduct taxProduct : impuestos) {

                    TaxValue value = taxProduct.getTaxValue();
                    Tax tax = value.getTax();

                    // EL ide retorna el codigo del impuesto
                    String codImpuesto = String.valueOf(tax.getIde());

                    if (codImpuesto.equalsIgnoreCase(TypeTaxEnum.IVA.getCode())) {
                        switch (value.getCode()) {
                            case "0":
                                subtotalCero = subtotalCero.add(BigDecimal.valueOf(item.getSubtotal()));
                                break;

                            case "7":
                                subtotalExcepto = subtotalExcepto.add(BigDecimal.valueOf(item.getSubtotal()));
                                break;
                            case "6":
                                subtotalNoObjeto = subtotalNoObjeto.add(BigDecimal.valueOf(item.getSubtotal()));
                                break;

                            case "2":
                                subtotalDoce = subtotalDoce.add(BigDecimal.valueOf(item.getSubtotal()));
                                break;
                        }

                    }

                    if (product.getIrbpnr().equalsIgnoreCase("SI") && codImpuesto.equalsIgnoreCase(TypeTaxEnum.IRBPNR.getCode())) {
                        irbprn = irbprn.add(BigDecimal.valueOf(0.02));
                    }

                    if (product.getIce().equalsIgnoreCase("SI") &&
                            codImpuesto.equalsIgnoreCase(TypeTaxEnum.ICE.getCode())) {

                        LOGGER.info("aQUI ES ICE " +  codImpuesto  + " " +  item.getValorIce()) ;

                        ice = ice.add(BigDecimal.valueOf(item.getValorIce()));

                    }
                }

            }

            subTotal = subTotal.add(subtotalCero).add(subtotalNoObjeto).add(subtotalExcepto).add(subtotalDoce);

            BigDecimal preIva = subtotalDoce.add(ice);

            iva = preIva.multiply(BigDecimal.valueOf(ConstanteUtil.IVA_ACTUAL_DECIMAL));

            total = subTotal.add(ice).add(irbprn).add(iva).add(propina);

            valuesDocuments.setSubtIvaCero(subtotalCero.doubleValue());
            valuesDocuments.setSubtExceptoIva(subtotalExcepto.doubleValue());
            valuesDocuments.setSubtNoObjIva(subtotalNoObjeto.doubleValue());
            valuesDocuments.setSubtIvaActual(subtotalDoce.doubleValue());

            valuesDocuments.setDescuento(descuento.doubleValue());
            valuesDocuments.setSubtotal(subTotal.doubleValue());
            valuesDocuments.setIce(ice.doubleValue());
            valuesDocuments.setRbpnr(irbprn.doubleValue());
            valuesDocuments.setIva(iva.doubleValue());
            valuesDocuments.setPropina(propina.doubleValue());
            valuesDocuments.setTotal(total.doubleValue());


            AdminInvoice.formatValues(valuesDocuments);
            LOGGER.debug("Valores calculados : " + valuesDocuments);

            return valuesDocuments;

        } catch (DataAccessException ex) {

            LOGGER.error("Error al realizar calculos", ex);
            throw new NotDataAccessException("Ocurrio un error al realizar los calculos para el documento");
        } catch (Exception ex) {
            LOGGER.error("Error inesperado al realizar calculos", ex);
            throw new NotDataAccessException("Ocurrio un error inesperado al realizar los calculos para el documento " + ex.getMessage());

        }

    }

    @Override
    public List<InvoiceResposeDTO> findBySender(Long idSender) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deletedById(Long id) {
        // TODO Auto-generated method stub

    }

    @Transactional(readOnly = true)
    @Override
    public int getCurrentSequentialNumberByEmissionPoint(String codDocument, Long idEmissionPoint) {

        return invoiceNumberRepository
                .findMaxCurrentSequentialNumberByEmissionPoint(codDocument, idEmissionPoint).orElse(0);
    }

    @Override
    @Transactional
    public void saveInvoiceNumber(InvoiceNumber invoiceNumber) {

        InvoiceNumber optionalInvoice = invoiceNumberRepository.findByTypeDocumentAndEmissionPointIde(
                invoiceNumber.getTypeDocument(), invoiceNumber.getEmissionPoint().getIde()).orElse(null);

        // Indica que un nuevo tipo de documento del emisor
        if (optionalInvoice == null) {

            // Nuevo registro
            invoiceNumberRepository.save(invoiceNumber);
        } else {

            // Indica que debe actualizar el numero de documento
            optionalInvoice.setCurrentSequentialNumber(invoiceNumber.getCurrentSequentialNumber());

            // Actualizar
            invoiceNumberRepository.save(optionalInvoice);
        }

    }

    /**
     * Buscca todos los producto agregados a la factura, la cual se utilizará para
     * calcular los valores de la factura
     *
     * @param idsProducts : lista de producto
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public List<Product> searchProductsDetailsByIds(List<Long> idsProducts) {

        // Validar que la factura tenga detalles
        if (idsProducts != null && idsProducts.size() > 0) {

            // Obtener los ids de los Productos
            List<Long> idsProduts = idsProducts.stream()
                    .filter(item -> (item != null && item > 0))
                    .collect(Collectors.toList());

            // Consultar los productos a la BD
            List<Product> products = productRepository.fetchTaxValueTaxByIdeIn(idsProduts);

            LOGGER.info("Longitud productos detalle: " + products.size());

            for (Product it : products) {
                LOGGER.debug(String.format("Producto detalle:  %s - %s", it.getIde(), it.getName()));
            }

            // Verificar que todos los productos del detall factura estubieran registrados en BD
            if (!products.isEmpty() && products.size() == idsProduts.size()) {
                // products.get(0).getTaxProducts().get(0).getTaxValue();
                return products;
            }

            throw new BadRequestException("La Factura contiene productos no registrados en la base de datos");

        }
        throw new BadRequestException("La Factura debe  contener al menos un detalle o más");
    }

    public ResponseWebServiceDTO consumeWebService(Invoice invoiceXML, int numberSecuncial) {

        LOGGER.debug("Dentro del web services");
        // LO que se enviara al frontED
        ResponseWebServiceDTO responseWebService = new ResponseWebServiceDTO();

        // Recepcion
        RespuestaSolicitud respuestaSolicitud = clientSRI.receptionDocument(pathFileSigned, WSTypeEnum.WS_TEST_RECEPTION);

        responseWebService.setRespuestaSolicitud(respuestaSolicitud);

        if (respuestaSolicitud != null && respuestaSolicitud.getEstado().equalsIgnoreCase("RECIBIDA")) {

            LOGGER.info("Web Service recepcion RECIBIDA");

            // Eliminar factura firmada
            AdminDocument.deleteDocument(pathFileSigned);


            // Realizar una espera de 3 segundo antes de invocar al web service de autorizacion
            try {
                Thread.sleep(ConstanteUtil.PAUSA_WS);

            } catch (InterruptedException e) {
                LOGGER.error("Error al hacer pasusa 3 segundos" + e.getMessage());
            }

            // Autorizacion
            RespuestaComprobante respuestaComprobante = clientSRI.authorizationDocument(WSTypeEnum.WS_TEST_AUTHORIZATION, facturaGenerada.getInfoTributaria().getClaveAcceso());

            LOGGER.info("Web service Autorizacion consumida");
            responseWebService.setRespuestaComprobante(respuestaComprobante);

            LOGGER.info("clave acceso consultada: " + respuestaComprobante.getClaveAccesoConsultada());

            LOGGER.debug("Numero comprobantes " + respuestaComprobante.getNumeroComprobantes());

            for (Autorizacion item : respuestaComprobante.getAutorizaciones().getAutorizacion()) {

                LOGGER.debug(String.format("Comprobante autorizacion %s ", item.getNumeroAutorizacion()));
                LOGGER.debug(String.format("Comprobante estado %s ", item.getEstado()));
                LOGGER.debug(String.format("Comprobante ambiente %s ", item.getAmbiente()));
                LOGGER.debug(String.format("Comprobante fecha %s ", item.getFechaAutorizacion()));
            }

            String estadoComprobate = respuestaComprobante.getAutorizaciones().getAutorizacion().get(0).getEstado();
            if (estadoComprobate.equalsIgnoreCase(StatusDocumentsEnum.AUTORIZADO.getName())) {
                // Actualizar contador de documentos si todo el proceso fue realizado con exito

                saveInvoiceNumber(AdminDocument.createInvoiceNumber(invoiceXML.getEmissionPoint(),
                        numberSecuncial,
                        facturaGenerada.getInfoTributaria().getCodDoc()));

                LOGGER.info(String.format("Secuencia actualizado: %s   estado: %s", numberSecuncial, estadoComprobate));

            } else
                LOGGER.info(String.format("Secuencia NO actualizado: %s   estado: %s", numberSecuncial, estadoComprobate));
            // Si el comprobante no fue RECIBIDO, se elimina tanto el comprobante generado como el que fue firmado
        } else {
            LOGGER.info("Web Service recepcion NO RECIBIDA");
            AdminDocument.deleteDocument(pathNewInvoiceXML);
            AdminDocument.deleteDocument(pathFileSigned);
        }

        return responseWebService;
    }


}
