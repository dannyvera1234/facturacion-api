package com.facturacion.ideas.api.services;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.facturacion.ideas.api.admin.AdminDocument;
import com.facturacion.ideas.api.dto.InvoiceNewDTO;
import com.facturacion.ideas.api.dto.InvoiceResposeDTO;
import com.facturacion.ideas.api.dto.ValueInvoiceNewDTO;
import com.facturacion.ideas.api.entities.EmissionPoint;
import com.facturacion.ideas.api.entities.Invoice;
import com.facturacion.ideas.api.entities.Sender;
import com.facturacion.ideas.api.entities.Subsidiary;
import com.facturacion.ideas.api.entities.ValueInvoice;
import com.facturacion.ideas.api.exeption.NotDataAccessException;
import com.facturacion.ideas.api.exeption.NotFoundException;
import com.facturacion.ideas.api.mapper.IDocumentMapper;
import com.facturacion.ideas.api.mapper.ISenderMapper;
import com.facturacion.ideas.api.mapper.SenderMapperImpl;
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
	public InvoiceResposeDTO saveInvoice(InvoiceNewDTO invoiceNewDTO) {
		try {

			// Por ahora provicioanl pasa ub objeto fijo- ELIMINAR ESTO DESPUES
			invoiceNewDTO.setValueInvoiceNewDTO(new ValueInvoiceNewDTO());

			// Valores de la factura
			ValueInvoice valueInvoice = documentMapper.mapperToEntity(invoiceNewDTO.getValueInvoiceNewDTO());

			// Crear factura
			Invoice invoice = documentMapper.mapperToEntity(invoiceNewDTO);

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

			// Buscar Punto de Emision y asignarlo a la factura

			EmissionPoint emissionPoint = emissionPointRepository.findById(invoiceNewDTO.getIdEmissionPoint())
					.orElseThrow(() -> new NotFoundException("Punto Emisión id " + invoiceNewDTO.getIdEmissionPoint()
							+ ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION));

			// El punto de emsion es boligatorio para la factuara
			invoice.setEmissionPoint(emissionPoint);

			Subsidiary subsidiary = emissionPoint.getSubsidiary();

			Sender sender = subsidiary.getSender();

			String keyAccess = AdminDocument.generateKeyAcces(invoiceNewDTO, sender, subsidiary, emissionPoint,
					getCurrentSequentialNumberBySubsidiary(subsidiary.getIde()));

			invoiceNewDTO.setKeyAccess(keyAccess);
			invoiceNewDTO.setNumberAutorization(keyAccess);

			Invoice invoiceSaved = invoiceRepository.save(invoice);

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

	@Override
	public int getCurrentSequentialNumberBySubsidiary(Long idSubsidiary) {

		int numberInvoiceOptional = invoiceNumberRepository.findMaxCurrentSequentialNumberBySubsidiary(idSubsidiary)
				.orElse(1);

		return numberInvoiceOptional;
	}

}
