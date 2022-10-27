package com.facturacion.ideas.api.services;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

import com.facturacion.ideas.api.dto.DeatailsInvoiceProductDTO;
import com.facturacion.ideas.api.entities.*;
import com.facturacion.ideas.api.exeption.BadRequestException;
import com.facturacion.ideas.api.repositories.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.facturacion.ideas.api.admin.AdminDocument;
import com.facturacion.ideas.api.documents.InfoTributaria;
import com.facturacion.ideas.api.documents.factura.InfoFactura;
import com.facturacion.ideas.api.dto.InvoiceNewDTO;
import com.facturacion.ideas.api.dto.InvoiceResposeDTO;
import com.facturacion.ideas.api.dto.ValueInvoiceNewDTO;
import com.facturacion.ideas.api.enums.TypeDocumentEnum;
import com.facturacion.ideas.api.enums.TypeIdentificationEnum;
import com.facturacion.ideas.api.exeption.NotDataAccessException;
import com.facturacion.ideas.api.exeption.NotFoundException;
import com.facturacion.ideas.api.mapper.IDocumentMapper;
import com.facturacion.ideas.api.util.ConstanteUtil;
import com.facturacion.ideas.api.util.FunctionUtil;

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
    public InvoiceResposeDTO saveInvoice(InvoiceNewDTO invoiceNewDTO) {
        try {

            // Buscar Punto de Emision de la factura
            EmissionPoint emissionPoint = emissionPointRepository.findById(invoiceNewDTO.getIdEmissionPoint())
                    .orElseThrow(() -> new NotFoundException("Punto Emisión id " + invoiceNewDTO.getIdEmissionPoint()
                            + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION));

            // Consultar los productos del detalle
            List<Product> products = searchProductsDetailsByIds(invoiceNewDTO.
                    getDeatailsInvoiceProductDTOs()
                        .stream()
                        .map(DeatailsInvoiceProductDTO::getIdProducto)
                        .collect(Collectors.toList()));

            // Asociar cada producto con su detalle correspondiente
            List<DeatailsInvoiceProduct> detalles = AdminDocument.createDeatailsInvoiceProduct(products, invoiceNewDTO.getDeatailsInvoiceProductDTOs());

            // Obtener los valores de la factura
            ValueInvoice valueInvoice = AdminDocument.calcularDetalleFactura(detalles);


            // Por ahora provicioanl pasa ub objeto fijo- ELIMINAR ESTO DESPUES
            //invoiceNewDTO.setValueInvoiceNewDTO(new ValueInvoiceNewDTO());

            // Valores de la factura
            //ValueInvoice valueInvoice = documentMapper.mapperToEntity(invoiceNewDTO.getValueInvoiceNewDTO());


            // Establecimiento del punto emision
            Subsidiary subsidiary = emissionPoint.getSubsidiary();

            // Emisor punto emision
            Sender sender = subsidiary.getSender();

            // Numero actual del documento + 1, segun su tipo de documento
            // IMPORTANTE: este numero se guardara en el factura valores
            int numberSecuncial = (getCurrentSequentialNumberBySubsidiary(invoiceNewDTO.getTypeDocument(),
                    subsidiary.getIde())) + 1;

            // Numero secuencia del Documento
            invoiceNewDTO.setNumberSecuencial(AdminDocument.nextSquentialNumberDocument(numberSecuncial));

            // Tipo de emision
            invoiceNewDTO.setTypoEmision(sender.getTypeEmission());

            String keyAccess = AdminDocument.generateKeyAcces(invoiceNewDTO, sender, subsidiary, emissionPoint);

            LOGGER.info("Clave acceso documento: " + keyAccess + " Longitud : " + keyAccess.length());

            invoiceNewDTO.setKeyAccess(keyAccess);
            invoiceNewDTO.setNumberAutorization(keyAccess);

            // Crear factura y mapear a Entitidad
            Invoice invoice = documentMapper.mapperToEntity(invoiceNewDTO);

            // El punto de emsion es boligatorio para la factuara
            invoice.setEmissionPoint(emissionPoint);

            // Agregar valores factura a la factura
            invoice.setValueInvoice(valueInvoice);

            /**
             * Asignar Persona a al factura, si es null, quiere decir que es consumidor
             * final y persisto en la relacion el valor null
             */
            if (invoiceNewDTO.getIdPerson() != null) {

                // Buscar Persona(cliente o Transportista) y asignar a al Factura
                invoice.setPerson(personRepository.findById(invoiceNewDTO.getIdPerson())
                        .orElseThrow(() -> new NotFoundException("Persona con id " + invoiceNewDTO.getIdPerson()
                                + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION)));

                // Asgino null, que representa a consumidor final
            } else
                invoice.setPerson(null);

            // Persistir la factura
            Invoice invoiceSaved = invoiceRepository.save(invoice);

            // Actualizar datos del numero secuencial del documento generado
            InvoiceNumber invoiceNumber = new InvoiceNumber();
            invoiceNumber.setSubsidiary(subsidiary);
            invoiceNumber.setCurrentSequentialNumber(numberSecuncial);
            invoiceNumber.setTypeDocument(TypeDocumentEnum.getTypeDocumentEnum(invoiceSaved.getTypeDocument()));

            // Guardar
            saveInvoiceNumber(invoiceNumber);

            return documentMapper.mapperToDTO(invoiceSaved);

        } catch (DataAccessException e) {
            LOGGER.error("Error guardar factura", e);
            throw new NotDataAccessException("Error al guardar factura");
        } catch (ParseException e) {

            LOGGER.error("Error al foramto fecha guardar factura", e);
            throw new NotDataAccessException("Error, formato de fechas de factura es incorrecto");
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
    public void saveInvoiceNumber(InvoiceNumber invoiceNumberCurrent) {

        try {

            InvoiceNumber optionalInvoice = invoiceNumberRepository.findByTypeDocumentAndSubsidiary(
                    invoiceNumberCurrent.getTypeDocument(), invoiceNumberCurrent.getSubsidiary()).orElse(null);

            // Indica que un nuevo tipo de documento del emisor
            if (optionalInvoice == null) {

                // Nuevo registro
                invoiceNumberRepository.save(invoiceNumberCurrent);
            } else {

                // Indica que debe actualizar el numero de documento
                optionalInvoice.setCurrentSequentialNumber(invoiceNumberCurrent.getCurrentSequentialNumber());

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

            int numberProducts = idsProducts.size();

            // Obtener los ids de los Productos
            List<Long> idsProduts = idsProducts.stream()
                    .filter(item -> (item!= null && item> 0))
                    .collect(Collectors.toList());

            // Verificar si todo los ids Productos  pasados diferentes de null
            if (!idsProduts.isEmpty() && idsProduts.size() == numberProducts) {

                // Consultar los productos a la BD
                List<Product> products = productRepository.findByIdeIn(idsProduts);

                // Verificar que todos los productos del detall factura estubieran registrados en BD
                if (!products.isEmpty() && products.size() == idsProduts.size())
                    return  products;

                throw new BadRequestException("La Factura contiene productos no registrados en la base de datos");
            }
            throw new BadRequestException("La Factura contiene productos con identificacion incorrecta");

        }
        throw new BadRequestException("La Factura debe  contener al menos un detalle o más");
    }


}
