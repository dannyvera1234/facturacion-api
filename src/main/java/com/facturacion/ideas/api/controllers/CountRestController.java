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
import com.facturacion.ideas.api.dto.CountNewDTO;
import com.facturacion.ideas.api.dto.CountResponseDTO;
import com.facturacion.ideas.api.dto.LoginDTO;
import com.facturacion.ideas.api.entities.Agreement;
import com.facturacion.ideas.api.entities.Count;
import com.facturacion.ideas.api.entities.DetailsAggrement;
import com.facturacion.ideas.api.exeption.NotDataAccessException;
import com.facturacion.ideas.api.exeption.NotFoundException;
import com.facturacion.ideas.api.services.IAgreementService;
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
	private IAgreementService agreementService;

	@Override
	public ResponseEntity<CountResponseDTO> save(CountNewDTO countNewDTO) {

		LOGGER.info("Cuenta a guardar: " + countNewDTO);

		try {
			CountResponseDTO countResponseDTO = senderService.saveCount(countNewDTO);
			return ResponseEntity.ok(countResponseDTO);

		} catch (NotDataAccessException e) {
			throw new NotDataAccessException(e.getMessage());

		}
	}

	@Override
	public ResponseEntity<CountResponseDTO> findById(Long id) {

		LOGGER.info("Id Cuenta a buscar: " + id);

		try {

			CountResponseDTO countResponseDTO = senderService.findCountsById(id);
			return ResponseEntity.ok(countResponseDTO);

		} catch (NotDataAccessException e) {
			throw new NotDataAccessException(e.getMessage());
		}

	}

	@Override
	public ResponseEntity<CountResponseDTO> findByRuc(String ruc) {

		LOGGER.info("ruc Cuenta a buscar: " + ruc);

		try {

			CountResponseDTO countResponseDTO = senderService.findCountByRuc(ruc);
			return ResponseEntity.ok(countResponseDTO);

		} catch (NotDataAccessException e) {
			throw new NotDataAccessException(e.getMessage());
		}

	}

	@Override
	public ResponseEntity<List<CountResponseDTO>> findAll() {
		try {

			List<CountResponseDTO> countResponseDTOs = senderService.findCountAll();

			return ResponseEntity.ok(countResponseDTOs);

		} catch (DataAccessException e) {

			throw new NotDataAccessException(e.getMessage());
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

				// countSave = senderService.saveCount(countSave);

				// return FunctionUtil.getResponseEntity(HttpStatus.OK, countSave);
				return FunctionUtil.getResponseEntity(HttpStatus.OK, null);

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

			senderService.deleteCountById(id);

			return ResponseEntity.noContent().build();

		} catch (NotDataAccessException e) {

			throw new NotDataAccessException(e.getMessage());

		}

	}

	@Override
	public ResponseEntity<LoginDTO> saveLogin(Long idCount) {

		LOGGER.info("Id Cuenta de Login guardar: " + idCount);

		try {

			LoginDTO loginDTO = senderService.saveLoginIn(idCount);

			return ResponseEntity.ok(loginDTO);

		} catch (NotDataAccessException e) {

			throw new NotDataAccessException(e.getMessage());

		}

	}

	@Override
	public ResponseEntity<?> saveDetailsAggrement(Long idCount, String codigoPlan) {

		LOGGER.info("Id cuenta " + idCount + " Id Plan : " + codigoPlan);
		
		/*try {

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

		}*/

		return FunctionUtil.getResponseEntity(HttpStatus.OK,"");
	}

	@Override
	public ResponseEntity<List<LoginDTO>> findAllLogin(Long idCount) {

		LOGGER.info("Id Cuenta de Login guardar: " + idCount);

		try {

			List<LoginDTO> loginDTOs = senderService.findAllLogin(idCount);

			return ResponseEntity.ok(loginDTOs);

		} catch (NotDataAccessException e) {

			throw new NotDataAccessException(e.getMessage());

		}

	}

}
