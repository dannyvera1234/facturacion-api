package com.facturacion.ideas.api.services;

import java.text.ParseException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.facturacion.ideas.api.admin.AdminDocument;
import com.facturacion.ideas.api.dto.InvoiceNewDTO;
import com.facturacion.ideas.api.dto.InvoiceResposeDTO;
import com.facturacion.ideas.api.dto.ValueInvoiceNewDTO;
import com.facturacion.ideas.api.entities.EmissionPoint;
import com.facturacion.ideas.api.entities.Invoice;
import com.facturacion.ideas.api.entities.InvoiceNumber;
import com.facturacion.ideas.api.entities.Sender;
import com.facturacion.ideas.api.entities.Subsidiary;
import com.facturacion.ideas.api.entities.ValueInvoice;
import com.facturacion.ideas.api.enums.TypeDocumentEnum;
import com.facturacion.ideas.api.exeption.NotDataAccessException;
import com.facturacion.ideas.api.exeption.NotFoundException;
import com.facturacion.ideas.api.mapper.IDocumentMapper;
import com.facturacion.ideas.api.repositories.IEmissionPointRepository;
import com.facturacion.ideas.api.repositories.IInvoiceNumberRepository;
import com.facturacion.ideas.api.repositories.IInvoiceRepository;
import com.facturacion.ideas.api.repositories.IPersonRepository;
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
	private IDocumentMapper documentMapper;

	@Override
	@Transactional
	public InvoiceResposeDTO saveInvoice(InvoiceNewDTO invoiceNewDTO) {
		try {

			// Por ahora provicioanl pasa ub objeto fijo- ELIMINAR ESTO DESPUES
			invoiceNewDTO.setValueInvoiceNewDTO(new ValueInvoiceNewDTO());

			// Valores de la factura
			ValueInvoice valueInvoice = documentMapper.mapperToEntity(invoiceNewDTO.getValueInvoiceNewDTO());

			// Buscar Punto de Emision y asignarlo a la factura
			EmissionPoint emissionPoint = emissionPointRepository.findById(invoiceNewDTO.getIdEmissionPoint())
					.orElseThrow(() -> new NotFoundException("Punto Emisión id " + invoiceNewDTO.getIdEmissionPoint()
							+ ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION));

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

		int numberInvoiceOptional = invoiceNumberRepository
				.findMaxCurrentSequentialNumberBySubsidiary(codDocument, idSubsidiary).orElse(0);

		return numberInvoiceOptional;
	}

	@Override
	@Transactional
	public void saveInvoiceNumber(InvoiceNumber invoiceNumberCurrent) {

		try {

			
			InvoiceNumber optionalInvoice = invoiceNumberRepository
					.findByTypeDocumentAndSubsidiary(invoiceNumberCurrent.getTypeDocument(), invoiceNumberCurrent.getSubsidiary())
					.orElse(null);

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

}
