package com.facturacion.ideas.api.controllers;

import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facturacion.ideas.api.admin.AdminDetailsAggrement;
import com.facturacion.ideas.api.controller.operation.ICountOperation;
import com.facturacion.ideas.api.entities.Agreement;
import com.facturacion.ideas.api.entities.Count;
import com.facturacion.ideas.api.entities.DetailsAggrement;
import com.facturacion.ideas.api.entities.Login;
import com.facturacion.ideas.api.exeption.DuplicatedResourceException;
import com.facturacion.ideas.api.exeption.NotDataAccessException;
import com.facturacion.ideas.api.exeption.NotFoundException;
import com.facturacion.ideas.api.services.IAgreementService;
import com.facturacion.ideas.api.services.ICodeDocumentService;
import com.facturacion.ideas.api.services.ISenderService;
import com.facturacion.ideas.api.util.ConstanteUtil;
import com.facturacion.ideas.api.util.FunctionUtil;

/**
 * RestController que expone servicios web para las entidades {@link Count} ,
 * {@link Agreement}
 * 
 * @author Ronny Chamba
 *
 */
@RestController
@RequestMapping("/facturacion/counts")
public class CountRestController implements ICountOperation {

	private static final Logger LOGGER = LogManager.getLogger(CountRestController.class);

	@Autowired
	private ISenderService senderService;

	@Autowired
	private ICodeDocumentService codeDocumentService;

	@Autowired
	private IAgreementService agreementService;

	@Override
	public ResponseEntity<?> save(Count count) {

		LOGGER.info("Cuenta a guardar: " + count);

		try {

			Optional<Count> countOptional = senderService.findCountByRuc(count.getRuc());

			if (countOptional.isEmpty()) {

				Count countSave = senderService.saveCount(count);

				return FunctionUtil.getResponseEntity(HttpStatus.CREATED, countSave);

			} else
				throw new DuplicatedResourceException("ruc: " + countOptional.get().getRuc()
						+ ConstanteUtil.MESSAJE_DUPLICATED_RESOURCE_DEFAULT_EXCEPTION);

		} catch (DataAccessException e) {

			LOGGER.error("Error al guardar cuenta: ", e);
			throw new NotDataAccessException("Error guardar Count: " + e.getMessage());

		}
	}

	@Override
	public ResponseEntity<?> findById(Long id) {

		LOGGER.info("Id Cuenta a buscar: " + id);

		try {

			Optional<Count> countOptional = senderService.findCountById(id);

			if (!countOptional.isEmpty()) {

				Count countSave = countOptional.get();
				// countSave.getSender();
				return FunctionUtil.getResponseEntity(HttpStatus.OK, countSave);

			} else
				throw new NotFoundException("id: " + id + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION);

		} catch (DataAccessException e) {

			LOGGER.error("Error al buscar cuenta: ", e);
			throw new NotDataAccessException("Error  buscar Count: " + e.getMessage());
		}

	}

	@Override
	public ResponseEntity<?> findAll() {
		try {

			List<Count> counts = senderService.findCountAll();

			return FunctionUtil.getResponseEntity(HttpStatus.OK, counts);

		} catch (DataAccessException e) {

			LOGGER.error("Error listar cuentas: ", e);
			throw new NotDataAccessException("Error  listar cuentas: " + e.getMessage());
		}
	}

	@Override
	public ResponseEntity<?> update(Count count, Long id) {

		LOGGER.info("Cuenta a actualizar: " + count);

		try {

			Optional<Count> countOptional = senderService.findCountById(id);

			if (!countOptional.isEmpty()) {

				Count countSave = countOptional.get();

				countSave.setEstado(count.isEstado());
				countSave.setPassword(count.getPassword());

				countSave = senderService.saveCount(countSave);

				return FunctionUtil.getResponseEntity(HttpStatus.OK, countSave);

			} else

				throw new NotFoundException("id: " + id + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION);

		} catch (DataAccessException e) {

			LOGGER.error("Error actualizar cuenta: ", e);

			throw new NotDataAccessException("Error actualizar cuenta: " + e.getMessage());

		}

	}

	@Override
	public ResponseEntity<?> deleteById(Long id) {

		LOGGER.info("Id Cuenta a eliminar: " + id);
		try {

			Optional<Count> countOptional = senderService.findCountById(id);

			if (!countOptional.isEmpty()) {

				Count countSave = countOptional.get();

				senderService.deleteCountById(countSave.getIde());

				// Eliminar los registros en la CodeDocument, que
				// esten relacionados con la cuenta recientemente elimnada
				codeDocumentService.deleteByIdCount(countSave.getIde());

				return FunctionUtil.getResponseEntity(HttpStatus.NO_CONTENT,
						countSave.getRuc() + " eliminado con exito");

			} else

				throw new NotFoundException("id: " + id + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION);

		} catch (DataAccessException e) {

			LOGGER.error("Error eliminar cuenta: ", e);

			throw new NotDataAccessException("Error eliminar cuenta: " + e.getMessage());

		}

	}

	@Override
	public ResponseEntity<?> saveLogin(Long idCount) {

		LOGGER.info("Id Cuenta de Login guardar: " + idCount);

		try {

			Optional<Count> count = senderService.findCountById(idCount);

			if (!count.isEmpty()) {

				Count countCurrent = count.get();
				countCurrent.addLogin(new Login());

				LOGGER.info("Cuenta de Login actual: " + countCurrent);

				countCurrent = senderService.updateCount(countCurrent);

				return FunctionUtil.getResponseEntity(HttpStatus.OK, countCurrent);

			} else
				throw new NotFoundException("id: " + idCount + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION);

		} catch (DataAccessException e) {

			LOGGER.error("Error guardar registro Login: ", e);

			throw new NotDataAccessException("Error guardar registro Login: " + e.getMessage());

		}

	}

	@Override
	public ResponseEntity<?> saveDetailsAggrement(Long idCount, String codigoPlan) {

		LOGGER.info("Id cuenta " + idCount + " Id Plan : " + codigoPlan);
		try {

			Optional<Count> count = senderService.findCountById(idCount);

			if (!count.isEmpty()) {

				Optional<Agreement> agreementOptional = agreementService.findById(codigoPlan);

				if (!agreementOptional.isEmpty()) {

					Count countCurrent = count.get();

					DetailsAggrement detailsAggrement = AdminDetailsAggrement.create(agreementOptional.get());

					countCurrent.addDetailsAggrement(detailsAggrement);

					countCurrent = senderService.updateCount(countCurrent);

					return FunctionUtil.getResponseEntity(HttpStatus.OK, countCurrent);
				} else

					throw new NotFoundException(
							"codigo Plan: " + codigoPlan + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION);

			} else
				throw new NotFoundException(
						"codigo cuenta: " + idCount + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION);

		} catch (DataAccessException e) {

			LOGGER.error("Error guardar registro contrato plan: ", e);
			throw new NotDataAccessException("Error guardar contrato Plan: " + e.getMessage());

		}

	}

}
