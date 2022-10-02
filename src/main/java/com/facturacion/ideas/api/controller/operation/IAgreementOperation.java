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

import com.facturacion.ideas.api.controllers.AgreementRestController;
import com.facturacion.ideas.api.dto.AgreementDTO;
import com.facturacion.ideas.api.entities.Agreement;
import com.facturacion.ideas.api.util.FunctionUtil;
/**
 *  Interface que define los contratos que debe realizar el {@link AgreementRestController}
 * @author Ronny Chamba
 *
 */
@RequestMapping("/default")
public interface IAgreementOperation {

	/**
	 * Inserta una nueva {@link Agreement} en la Base de Datos <br>
	 * <b>Http: </b> {@link PostMapping}
	 * @param agreement : Objeto a insertar
	 * @return {@link FunctionUtil#getResponseEntity(HttpStatus, Object, String)}
	 */
	@PostMapping
	public ResponseEntity<AgreementDTO> save(@RequestBody AgreementDTO agreementDTO);

	/**
	 * Recupera todas los {@link Agreement } regisradas en Base de Datos <br>
	 * <b>Http:</b> {@link GetMapping}
	 * @return {@link FunctionUtil#getResponseEntity(HttpStatus, Object, String)}
	 */
	@GetMapping
	public ResponseEntity<List<AgreementDTO>> findAll();

	/**
	 * Busca una {@link Agreement} a traves de su codigo <br>
	 * <b>Http: </b> {@link GetMapping}
	 * @param codigo : Codigo del Plan
	 * @return {@link FunctionUtil#getResponseEntity(HttpStatus, Object, String)}
	 */
	@GetMapping("/{codigo}")
	public ResponseEntity<AgreementDTO> findById(@PathVariable(required = false) String codigo);

	/**
	 * Elimina {@link Agreement} a traves de su codigo <br>
	 * <b>Http: </b> {@link DeleteMapping}
	 * @param codigo : Codigo del Plan
	 * @return {@link FunctionUtil#getResponseEntity(HttpStatus, Object, String)}
	 */
	@DeleteMapping("/{codigo}")
	public ResponseEntity<?> deleteById(@PathVariable(required = false) String codigo);

	/**
	 * Actualiza una {@link Agreement} <br>
	 * <b>Http: </b> {@link PutMapping}
	 * @param agreement : Un {@link Agreement} con los nuevos datos
	 * @param codigo : codigo del Agreement a actualizar
	 * @return  {@link FunctionUtil#getResponseEntity(HttpStatus, Object, String)}
	 */
	@PutMapping("/{codigo}")
	public ResponseEntity<AgreementDTO> update(@RequestBody AgreementDTO agreementDTO, @PathVariable(required = false) String codigo);
}
