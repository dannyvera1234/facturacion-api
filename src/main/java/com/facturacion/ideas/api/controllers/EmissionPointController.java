package com.facturacion.ideas.api.controllers;

import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facturacion.ideas.api.admin.AdminEmissionPoint;
import com.facturacion.ideas.api.controller.operation.IEmissionPointOperation;
import com.facturacion.ideas.api.entities.CodeDocument;
import com.facturacion.ideas.api.entities.Count;
import com.facturacion.ideas.api.entities.EmissionPoint;
import com.facturacion.ideas.api.entities.Subsidiary;
import com.facturacion.ideas.api.services.ICodeDocumentService;
import com.facturacion.ideas.api.services.IEmissionPointService;
import com.facturacion.ideas.api.services.ISubsidiaryService;
import com.facturacion.ideas.api.util.FunctionUtil;

@RestController
@RequestMapping("/facturacion/subsidiarys/{codigo}/emissions")
public class EmissionPointController implements IEmissionPointOperation {

	private static final Logger LOGGER = LogManager.getLogger(EmissionPointController.class);

	@Autowired
	private IEmissionPointService emissionPointService;

	@Autowired
	private ISubsidiaryService subsidiaryService;

	@Autowired
	private ICodeDocumentService codeDocumentService;

	@Override
	public ResponseEntity<?> save(Long codigo, EmissionPoint emissionPoint) {

		LOGGER.info("Id Establecimiento para el PuntoEmision: " + codigo);

		ResponseEntity<Map<String, Object>> responseEntity = null;

		try {

			Optional<Subsidiary> subsidiaryOptional = subsidiaryService.findById(codigo);

			if (!subsidiaryOptional.isEmpty()) {

				Subsidiary subsidiaryCurrent = subsidiaryOptional.get();

				Count countCurrent = subsidiaryCurrent.getSender().getCount();

				// Consultar en CodeDocument , el registro del establecimiento consultado pero
				// que tenga el id de Cuenta

				// codigo del establecimiento al cual se desea agregar un nuevo punto de emision
				String codeSubsidiary = subsidiaryCurrent.getCode();

				// Obtiene el codigo de la cuenta a la que pertenece el Establecimiento
				// consultado
				Long codeCount = countCurrent.getIde();

				// Obtiene el registro de CodeDocument, para poder obtener el numero
				// de punto de emision del establecimiento y poder generar el siguiente
				// secuencial
				Optional<CodeDocument> codeOptional = codeDocumentService.findByCodeCountAndCodeSubsidiary(codeCount,
						codeSubsidiary);

				if (!codeOptional.isEmpty()) {
					
					CodeDocument codeDocumentCurrent = codeOptional.get();

					// Crear el EmissionPoint
					emissionPoint = AdminEmissionPoint.create(codeDocumentCurrent.getNumEmissionPoint(),
							countCurrent.getRuc());

					emissionPoint.setSubsidiary(subsidiaryCurrent);

					// Guardar el nuevo EmissionPoint
					EmissionPoint emissionPointSave = emissionPointService.save(emissionPoint);

					// Actualizar el numero secuencial del punto Emision del establecimiento
					codeDocumentCurrent.setNumEmissionPoint(codeDocumentCurrent.getNumEmissionPoint() + 1);
					codeDocumentService.save(codeDocumentCurrent);

					responseEntity = FunctionUtil.getResponseEntity(HttpStatus.CREATED,

							emissionPointSave.getCodePoint(), null);

				} else
					responseEntity = FunctionUtil.getResponseEntity(HttpStatus.BAD_REQUEST,

							null, "No se pudo crear el punto Emision, no se accedio al numero secuencial");

			} else
				responseEntity = FunctionUtil.getResponseEntity(HttpStatus.NOT_FOUND, null,
						"Establecimiento con Id " + codigo + " no esta registrado en la Base de Datos");

		} catch (DataAccessException e) {
			LOGGER.error("Error al guardar Punto de emisions:", e);

			responseEntity = FunctionUtil.getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, null,
					e.getMessage() + " : " + e.getMostSpecificCause());
		}

		return responseEntity;
	}

	@Override
	public ResponseEntity<?> findAll(Long codigo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<?> findById(Long codigo, Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<?> deleteById(Long codigo, Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<?> update(EmissionPoint emissionPoint, Long codigo, Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}