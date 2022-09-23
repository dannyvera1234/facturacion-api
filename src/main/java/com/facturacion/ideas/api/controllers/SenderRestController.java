package com.facturacion.ideas.api.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facturacion.ideas.api.entities.Count;
import com.facturacion.ideas.api.entities.Sender;
import com.facturacion.ideas.api.enums.ProvinceEnum;
import com.facturacion.ideas.api.enums.TypeEmissionEnum;
import com.facturacion.ideas.api.enums.TypeEnvironmentEnum;
import com.facturacion.ideas.api.services.ISenderService;

@RestController
@RequestMapping("/facturacion/senders")
public class SenderRestController {

	private static final Logger LOGGER = LogManager.getLogger(SenderRestController.class);

	@Autowired
	private ISenderService senderService;

	@PostMapping("/{id}")
	public ResponseEntity<Map<String, Object>> saveSender(@RequestBody Sender sender, @PathVariable Long id) {

		LOGGER.info("Id Cuenta Emisor: " + id);

		ResponseEntity<Map<String, Object>> responseEntity = null;

		try {

			// Verificar si existe la cuenta
			Optional<Count> countOptional = senderService.findCountById(id);

			if (!countOptional.isEmpty()) {

				Count countCurrent = countOptional.get();

				// Verificar si ya existe un emisor con el Ruc
				Optional<Boolean> senderIsExist = senderService.senderIsExiste(countCurrent.getRuc());

				LOGGER.info("Ya existe Sender?:" + senderIsExist);

				if (senderIsExist.isEmpty()) {

					// Por seguridad seteo el ruc de la cuenta
					sender.setRuc(countCurrent.getRuc());

					sender.setCount(countCurrent);

					// Persistir el Emisor
					Sender senderCurrent = senderService.saveSender(sender);
					LOGGER.info("Sender guardado : " + senderCurrent);

					responseEntity = getResponseEntity(HttpStatus.CREATED, senderCurrent, null);

				} else
					responseEntity = getResponseEntity(HttpStatus.BAD_REQUEST, null,
							"Emisor con ruc " + countCurrent.getRuc() + " ya esta registrado");

			} else
				responseEntity = getResponseEntity(HttpStatus.NOT_FOUND, null,
						"Cuenta con id " + id + " no esta registrada");

		} catch (DataAccessException e) {
			LOGGER.error("Error al guardar emisor:", e);

			responseEntity = getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, null,
					e.getMessage() + " : " + e.getMostSpecificCause());

		}

		return responseEntity;

	}

	@GetMapping("/{id}")
	public ResponseEntity<Map<String, Object>> findSenderById(@PathVariable Long id) {

		ResponseEntity<Map<String, Object>> responseEntity = null;

		try {

			Optional<Sender> senderOptional = senderService.findSenderById(id);

			if (!senderOptional.isEmpty()) {
				responseEntity = getResponseEntity(HttpStatus.OK, senderOptional.get(), null);
			} else
				responseEntity = getResponseEntity(HttpStatus.NOT_FOUND, null,
						"El emisor con id " + id + " no esta registrado");

		} catch (DataAccessException e) {
			LOGGER.error("Error al buscar emisor:", e);

			responseEntity = getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, null,
					e.getMessage() + " : " + e.getMostSpecificCause());

		}

		return responseEntity;

	}

	@GetMapping
	public ResponseEntity<Map<String, Object>> findSenderAll() {

		ResponseEntity<Map<String, Object>> responseEntity = null;

		try {

			List<Sender> senders = senderService.findSenderAll();

			responseEntity = getResponseEntity(HttpStatus.OK, senders, null);

		} catch (DataAccessException e) {
			LOGGER.error("Error al listar todos emisor:", e);

			responseEntity = getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, null,
					e.getMessage() + " : " + e.getMostSpecificCause());

		}

		return responseEntity;

	}

	@PutMapping("/{id}")
	public ResponseEntity<Map<String, Object>> updateSender(@RequestBody Sender sender, @PathVariable Long id) {

		LOGGER.info("Id Emisor: " + id);

		ResponseEntity<Map<String, Object>> responseEntity = null;

		try {

			// Verificar si existe el emisor
			Optional<Sender> senderOptional = senderService.findSenderById(id);

			if (!senderOptional.isEmpty()) {

				Sender senderCurrent = senderOptional.get();

				// Setear nuevos valores

				senderCurrent.setSocialReason(
						sender.getSocialReason() == null ? senderCurrent.getSocialReason() : sender.getSocialReason());

				senderCurrent.setCommercialName(sender.getCommercialName() == null ? senderCurrent.getCommercialName() :

						sender.getCommercialName());
				senderCurrent.setLogo(sender.getLogo() == null ? senderCurrent.getLogo() : sender.getLogo());
				senderCurrent.setProvince(

						sender.getProvince() == null ? ProvinceEnum.getProvinceEnum(senderCurrent.getProvince())
								: ProvinceEnum.getProvinceEnum(sender.getProvince()));
				senderCurrent.setTypeEnvironment(

						sender.getTypeEnvironment() == null
								? TypeEnvironmentEnum.getTypeEnvironmentEnum(senderCurrent.getTypeEnvironment())
								:

								TypeEnvironmentEnum.getTypeEnvironmentEnum(sender.getTypeEnvironment()));
				senderCurrent.setMatrixAddress(sender.getMatrixAddress() == null ? senderCurrent.getMatrixAddress()
						: sender.getMatrixAddress());

				senderCurrent.setSpecialContributor(

						sender.getSpecialContributor() == null ? senderCurrent.getSpecialContributor()
								: sender.getSpecialContributor());

				senderCurrent.setTypeEmission(

						sender.getTypeEmission() == null
								? TypeEmissionEnum.getTypeEmissionEnum(senderCurrent.getTypeEmission())
								: TypeEmissionEnum.getTypeEmissionEnum(sender.getTypeEmission()));

				senderCurrent.setTypeSender(
						sender.getTypeSender() == null ? senderCurrent.getTypeSender() : sender.getTypeSender());

				senderCurrent.setAccountancy(
						sender.getAccountancy() == null ? senderCurrent.getAccountancy() : sender.getAccountancy());

				LOGGER.info("Emisor antes de  actualizar : " + senderCurrent);

				Sender senderUpdate = senderService.saveSender(senderCurrent);

				LOGGER.info("Emisor actualizado : " + senderUpdate);
				responseEntity = getResponseEntity(HttpStatus.OK, senderUpdate, null);

			} else
				responseEntity = getResponseEntity(HttpStatus.NOT_FOUND, null,
						"Emisor con id " + id + " no esta registrado");

		} catch (DataAccessException e) {
			LOGGER.error("Error al actualizar emisor:", e);

			responseEntity = getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, null,
					e.getMessage() + " : " + e.getMostSpecificCause());

		}

		return responseEntity;

	}

	private ResponseEntity<Map<String, Object>> getResponseEntity(HttpStatus status, Object data, String error) {

		Map<String, Object> responseData = new HashMap<>();
		responseData.put("status", status);
		responseData.put("data", data);
		responseData.put("error", error);

		return new ResponseEntity<>(responseData, status);
	}

}
