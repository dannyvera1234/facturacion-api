package com.facturacion.ideas.api.services;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

import com.facturacion.ideas.api.admin.AdminInvoice;
import com.facturacion.ideas.api.documents.factura.Factura;
import com.facturacion.ideas.api.entities.*;
import com.facturacion.ideas.api.enums.TypeDocumentEnum;
import com.facturacion.ideas.api.exeption.BadRequestException;
import com.facturacion.ideas.api.exeption.GenerateXMLExeption;
import com.facturacion.ideas.api.repositories.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.facturacion.ideas.api.admin.AdminDocument;
import com.facturacion.ideas.api.dto.InvoiceNewDTO;
import com.facturacion.ideas.api.dto.InvoiceResposeDTO;
import com.facturacion.ideas.api.exeption.NotDataAccessException;
import com.facturacion.ideas.api.exeption.NotFoundException;
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

    @Override
    @Transactional
    public InvoiceResposeDTO saveInvoice(final InvoiceNewDTO invoiceNewDTO) {
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
            int numberSecuncial = (getCurrentSequentialNumberBySubsidiary(invoiceNewDTO.getTypeDocument(),
                    subsidiary.getIde())) + 1;

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

            AdminInvoice.generatorFractureXML(invoiceXML, invoiceNewDTO, products);

            Factura facturaGenerada = AdminInvoice.getFacturaGenerada();


            /*
            // Obtener los valores de la factura
            ValueInvoice valueInvoice = AdminInvoice.calcularDetalleFactura(detalles, 0.0);

            // Crear factura y mapear a Entitidad, no se mapea las relaciones, en el mapper ya se asigno la
            // relacion con formas de pago
            Invoice invoice = documentMapper.mapperToEntity(invoiceNewDTO);

            // Asignar detalles
            invoice.setDeatailsInvoiceProducts(detalles);

            // Agregar valores factura a la factura
            invoice.setValueInvoice(valueInvoice);


            // Persistir la factura
            Invoice invoiceSaved = invoiceRepository.save(invoice);

            // Actualizar contador de documetos
            saveInvoiceNumber(AdminDocument.createInvoiceNumber(subsidiary, numberSecuncial, invoiceSaved.getTypeDocument()));


            return documentMapper.mapperToDTO(invoiceSaved);*/

            return new InvoiceResposeDTO();

        } catch (DataAccessException e) {
            LOGGER.error("Error guardar factura", e);
            throw new NotDataAccessException("Error al guardar factura");
        } catch (GenerateXMLExeption e) {
            LOGGER.error(e.getMessage(), e);
            throw new GenerateXMLExeption(e.getMessage());
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
    public int getCurrentSequentialNumberBySubsidiary(String codDocument, Long idSubsidiary) {

        return invoiceNumberRepository
                .findMaxCurrentSequentialNumberBySubsidiary(codDocument, idSubsidiary).orElse(0);
    }

    @Override
    @Transactional
    public void saveInvoiceNumber(InvoiceNumber invoiceNumber) {

        try {
            InvoiceNumber optionalInvoice = invoiceNumberRepository.findByTypeDocumentAndSubsidiary(
                    invoiceNumber.getTypeDocument(), invoiceNumber.getSubsidiary()).orElse(null);

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

        } catch (DataAccessException e) {
            LOGGER.error("Error al guardar numero de factura", e);
            throw new NotDataAccessException("Error al guardar numero factura");

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
            System.out.println(products);

            // Verificar que todos los productos del detall factura estubieran registrados en BD
            if (!products.isEmpty() && products.size() == idsProduts.size()){
               // products.get(0).getTaxProducts().get(0).getTaxValue();
                return products;
            }

            throw new BadRequestException("La Factura contiene productos no registrados en la base de datos");

        }
        throw new BadRequestException("La Factura debe  contener al menos un detalle o más");
    }


}
