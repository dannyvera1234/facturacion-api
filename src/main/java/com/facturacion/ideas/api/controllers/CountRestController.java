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
import com.facturacion.ideas.api.services.IAgreementService;
import com.facturacion.ideas.api.services.ICodeDocumentService;
import com.facturacion.ideas.api.services.ISenderService;
import com.facturacion.ideas.api.util.FunctionUtil;

/**
 * RestController que expone servicios web para las entidades {@link Count} , {@link Agreement}
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

		ResponseEntity<?> responseEntity = null;

		try {

			Optional<Count> countOptional = senderService.findCountByRuc(count.getRuc());

			if (countOptional.isEmpty()) {

				Count countSave = senderService.saveCount(count);

				responseEntity = FunctionUtil.getResponseEntity(HttpStatus.CREATED, countSave, null);

			} else {

				responseEntity = FunctionUtil.getResponseEntity(HttpStatus.BAD_REQUEST, null,
						"El Ruc " + countOptional.get().getRuc() + " ya esta registrado");
			}

		} catch (DataAccessException e) {

			LOGGER.error("Error al guardar cuenta: ", e);
			responseEntity = FunctionUtil.getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, null,
					e.getMessage() + " " + e.getMostSpecificCause());

		}

		return responseEntity;
	}

	@Override
	public ResponseEntity<?> findById(Long id) {
		LOGGER.info("Id Cuenta a buscar: " + id);

		ResponseEntity<?> responseEntity = null;

		try {

			Optional<Count> countOptional = senderService.findCountById(id);

			if (!countOptional.isEmpty()) {

				Count countSave = countOptional.get();
				// countSave.getSender();
				responseEntity = FunctionUtil.getResponseEntity(HttpStatus.OK, countSave, null);

			} else {

				responseEntity = FunctionUtil.getResponseEntity(HttpStatus.NOT_FOUND, null,
						"La cuenta  " + id + " no esta registrada");
			}

		} catch (DataAccessException e) {

			LOGGER.error("Error al buscar cuenta: ", e);
			responseEntity = FunctionUtil.getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, null,
					e.getMessage() + " " + e.getMostSpecificCause());

		}

		return responseEntity;
	}

	@Override
	public ResponseEntity<?> findAll() {

		ResponseEntity<?> responseEntity = null;

		try {

			List<Count> counts = senderService.findCountAll();

			responseEntity = FunctionUtil.getResponseEntity(HttpStatus.OK, counts, null);

		} catch (DataAccessException e) {

			LOGGER.error("Error al listar cuentas: ", e);
			responseEntity = FunctionUtil.getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, null,
					e.getMessage() + " " + e.getMostSpecificCause());

		}

		return responseEntity;
	}

	@Override
	public ResponseEntity<?> update(Count count, Long id) {

		LOGGER.info("Cuenta a actualizar: " + count);

		ResponseEntity<?> responseEntity = null;

		try {

			Optional<Count> countOptional = senderService.findCountById(id);

			if (!countOptional.isEmpty()) {

				Count countSave = countOptional.get();

				countSave.setEstado(count.isEstado());
				countSave.setPassword(count.getPassword());

				countSave = senderService.saveCount(countSave);

				responseEntity = FunctionUtil.getResponseEntity(HttpStatus.OK, countSave, null);

			} else {

				responseEntity = FunctionUtil.getResponseEntity(HttpStatus.BAD_REQUEST, null,
						"La cuenta  " + count.getIde() + " no esta registrada");
			}

		} catch (DataAccessException e) {

			LOGGER.error("Error al actualizar cuenta: ", e);
			responseEntity = FunctionUtil.getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, null,
					e.getMessage() + " " + e.getMostSpecificCause());

		}

		return responseEntity;
	}

	@Override
	public ResponseEntity<?> deleteById(Long id) {

		LOGGER.info("Id Cuenta a eliminar: " + id);

		ResponseEntity<?> responseEntity = null;

		try {

			Optional<Count> countOptional = senderService.findCountById(id);

			if (!countOptional.isEmpty()) {

				Count countSave = countOptional.get();

				senderService.deleteCountById(countSave.getIde());

				// Eliminar los registros en la CodeDocument, que
				// esten relacionados con la cuenta recientemente elimnada
				codeDocumentService.deleteByIdCount(countSave.getIde());

				responseEntity = FunctionUtil.getResponseEntity(HttpStatus.OK, null, null);

			} else {

				responseEntity = FunctionUtil.getResponseEntity(HttpStatus.NOT_FOUND, null,
						"La cuenta  " + id + " no esta registrada");
			}

		} catch (DataAccessException e) {

			LOGGER.error("Error al eliminar cuenta: ", e);
			responseEntity = FunctionUtil.getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, null,
					e.getMessage() + " " + e.getMostSpecificCause());

		}

		return responseEntity;

	}

	@Override
	public ResponseEntity<?> saveLogin(Long idCount) {

		LOGGER.info("Id Cuenta de Login guardar: " + idCount);

		ResponseEntity<?> responseEntity = null;

		try {

			Optional<Count> count = senderService.findCountById(idCount);

			if (!count.isEmpty()) {

				Count countCurrent = count.get();
				countCurrent.addLogin(new Login());

				LOGGER.info("Cuenta de Login actual: " + countCurrent);

				countCurrent = senderService.updateCount(countCurrent);

				responseEntity = FunctionUtil.getResponseEntity(HttpStatus.OK, countCurrent, null);

			} else
				responseEntity = FunctionUtil.getResponseEntity(HttpStatus.NOT_FOUND, null,
						"Cuenta con id " + idCount + " no esta registrado");

		} catch (DataAccessException e) {

			LOGGER.error("Error al guardar registro Login: ", e);
			responseEntity = FunctionUtil.getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, null,
					e.getMessage() + " " + e.getMostSpecificCause());

		}

		return responseEntity;

	}

	/*
	 * 
	 * }
	 * 
	 * @GetMapping("/{id}/login") public ResponseEntity<?>
	 * getListLogin(@PathVariable Long id) {
	 * 
	 * LOGGER.info("Id Cuenta: " + id);
	 * 
	 * ResponseEntity<?> responseEntity = null;
	 * 
	 * try {
	 * 
	 * Optional<Count> count = senderService.findCountById(id);
	 * 
	 * if (!count.isEmpty()) { List<Login> listLogins = count.get().getLogins();
	 * 
	 * responseEntity = getResponseEntity(HttpStatus.OK, listLogins, null);
	 * 
	 * } else responseEntity = getResponseEntity(HttpStatus.NOT_FOUND, null,
	 * "Cuenta Id " + id + " no registrada");
	 * 
	 * } catch (DataAccessException e) {
	 * 
	 * LOGGER.error("Error al listar Logins: ", e); responseEntity =
	 * getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, null, e.getMessage() +
	 * " " + e.getMostSpecificCause());
	 * 
	 * }
	 * 
	 * return responseEntity;
	 * 
	 * }
	 */
	
	@Override
	public ResponseEntity<?> saveDetailsAggrement(Long idCount, String codigoPlan) {

		LOGGER.info("Id cuenta " + idCount + " Id Plan : " + codigoPlan);

		ResponseEntity<?> responseEntity = null;

		try {

			Optional<Count> count = senderService.findCountById(idCount);

			if (!count.isEmpty()) {

				Optional<Agreement> agreementOptional = agreementService.findById(codigoPlan);

				if (!agreementOptional.isEmpty()) {

					Count countCurrent = count.get();

					DetailsAggrement detailsAggrement = AdminDetailsAggrement.create(agreementOptional.get());

					countCurrent.addDetailsAggrement(detailsAggrement);

					countCurrent = senderService.updateCount(countCurrent);

					responseEntity = FunctionUtil.getResponseEntity(HttpStatus.OK, countCurrent, null);
				} else
					responseEntity = FunctionUtil.getResponseEntity(HttpStatus.NOT_FOUND, null,

							"Plan codigo " + codigoPlan + " no registrado");

			} else
				responseEntity = FunctionUtil.getResponseEntity(HttpStatus.NOT_FOUND, null,

						"Cuenta Id " + idCount + " no registrada");

		} catch (DataAccessException e) {

			LOGGER.error("Error al guardar registro contrato plan: ", e);
			responseEntity = FunctionUtil.getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, null,
					e.getMessage() + " " + e.getMostSpecificCause());

		}

		return responseEntity;
	}

}
