package com.facturacion.ideas.api.controller.operation;

import java.util.List;

import com.facturacion.ideas.api.dto.SubsidiaryAndEmissionPointDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.facturacion.ideas.api.controllers.SenderRestController;
import com.facturacion.ideas.api.dto.SenderNewDTO;
import com.facturacion.ideas.api.dto.SenderResponseDTO;
import com.facturacion.ideas.api.entities.Sender;
import com.facturacion.ideas.api.util.FunctionUtil;

/**
 * Interface que define los contratos que debe realizar el
 * {@link SenderRestController}
 * 
 * @author Ronny Chamba
 *
 */
public interface ISenderOperation {

	/**
	 * Inserta una nueva {@link Sender} en la Base de Datos
	 * 
	 * @param sender  : Objeto a insertar
	 * @param idCount : Id de la Count
	 * @return {@link FunctionUtil#getResponseEntity(HttpStatus, Object, String)}
	 */
	@PostMapping("/counts/{id}/senders")
	public ResponseEntity<SenderResponseDTO> save(@RequestBody SenderNewDTO sender, @PathVariable("id") Long idCount);

	/**
	 * Recupera todas las {@link Sender} regisradas en Base de Datos
	 * 
	 * @return Respuesta
	 *         {@link FunctionUtil#getResponseEntity(HttpStatus, Object, String)}
	 */
	@GetMapping("/senders")
	public ResponseEntity<List<SenderResponseDTO>> findAll();

	/**
	 * Busca una {@link Sender} a traves de su id
	 * 
	 * @param id : Id de Sender
	 * @return Respuesta
	 *         {@link FunctionUtil#getResponseEntity(HttpStatus, Object, String)}
	 */
	@GetMapping("/senders/{id}")
	public ResponseEntity<SenderResponseDTO> findById(@PathVariable(required = false) Long id);

	@GetMapping("/senders/search")
	public ResponseEntity<SenderResponseDTO> findByRuc(@RequestParam(required = false, defaultValue = "") String ruc);

	/**
	 * Actualiza una {@link Sender}
	 * 
	 * @param count : Una {@link Count} con los nuevos datos
	 * @param id    : Id de la Count a actualizar
	 * @return Respuesta
	 *         {@link FunctionUtil#getResponseEntity(HttpStatus, Object, String)}
	 *         con los nuevos datos actualizados
	 */

	/**
	 * Actualiza una {@link Sender}
	 * 
	 * @param senderNewDTO : Un Sender con los nuevos datos
	 * @param id     : Id de la Count a la que pertenece
	 * @return {@link FunctionUtil#getResponseEntity(HttpStatus, Object, String)}
	 *         con los nuevos datos actualizados
	 */
	@PutMapping("/senders/{id}")
	public ResponseEntity<SenderResponseDTO> update(@RequestBody SenderNewDTO senderNewDTO, @PathVariable Long id);

}
