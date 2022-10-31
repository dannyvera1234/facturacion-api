package com.facturacion.ideas.api.controller.operation;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.facturacion.ideas.api.controllers.AdminAgreementRestController;
import com.facturacion.ideas.api.dto.AgreementDTO;
import com.facturacion.ideas.api.entities.Agreement;
import com.facturacion.ideas.api.util.FunctionUtil;
/**
 *  Interface que define los contratos que debe realizar el {@link AdminAgreementRestController}
 * @author Ronny Chamba
 *
 */
@RequestMapping("/default")
public interface IAgreementOperation {


	/**
	 * Elimina {@link Agreement} a traves de su codigo <br>
	 * <b>Http: </b> {@link DeleteMapping}
	 * @param codigo : Codigo del Plan
	 * @return {@link FunctionUtil#getResponseEntity(HttpStatus, Object, String)}
	 */
	@DeleteMapping("/{codigo}")
	public ResponseEntity<?> deleteById(@PathVariable(required = false) Long codigo);

	/**
	 * Actualiza una {@link Agreement} <br>
	 * <b>Http: </b> {@link PutMapping}
	 * @param agreement : Un {@link Agreement} con los nuevos datos
	 * @param codigo : codigo del Agreement a actualizar
	 * @return  {@link FunctionUtil#getResponseEntity(HttpStatus, Object, String)}
	 */
	@PutMapping("/{codigo}")
	public ResponseEntity<AgreementDTO> update(@RequestBody AgreementDTO agreementDTO, @PathVariable(required = false) Long codigo);
}
