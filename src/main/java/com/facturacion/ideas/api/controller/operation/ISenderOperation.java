package com.facturacion.ideas.api.controller.operation;

import java.util.List;

import com.facturacion.ideas.api.dto.SubsidiaryAndEmissionPointDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.facturacion.ideas.api.controllers.SenderRestController;
import com.facturacion.ideas.api.dto.SenderNewDTO;
import com.facturacion.ideas.api.dto.SenderResponseDTO;
import com.facturacion.ideas.api.entities.Sender;
import com.facturacion.ideas.api.util.FunctionUtil;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

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
	 * @param senderNewDTO  : Objeto a insertar
	 * @return {@link FunctionUtil#getResponseEntity(HttpStatus, Object, String)}
	 */
	@PreAuthorize(value = "hasRole('ROLE_USER')")
	@PostMapping(value = "/senders")
	 ResponseEntity<SenderResponseDTO> save(
			 @RequestParam("senderNewDTO") String senderNewDTO,
				@RequestParam (value = "logo", required = false) MultipartFile logo,
			 @RequestParam("certificado") MultipartFile certificado);

	// public ResponseEntity<SenderResponseDTO> save(@RequestBody SenderNewDTO sender, @PathVariable("id") Long idCount);
	/**
	 * Busca una {@link Sender} a traves de su id
	 * 
	 * @param id : Id de Sender
	 * @return Respuesta
	 *         {@link FunctionUtil#getResponseEntity(HttpStatus, Object, String)}
	 */
	@GetMapping("/senders/{id}")
	 ResponseEntity<SenderResponseDTO> findById(@PathVariable(required = false) Long id);

	@GetMapping("/senders/edit")
	ResponseEntity<SenderNewDTO> findToEdit();

	@GetMapping("/senders/search")
	 ResponseEntity<SenderResponseDTO> findByRuc(@RequestParam(required = false, defaultValue = "") String ruc);

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
	 * @param logo     : Logo
	 *@param certificado     : certificado
	 * @return {@link FunctionUtil#getResponseEntity(HttpStatus, Object, String)}
	 *         con los nuevos datos actualizados
	 */
	@PreAuthorize(value = "hasRole('ROLE_USER')")
	@PutMapping("/senders")
	 ResponseEntity<SenderResponseDTO> update(
			@RequestParam("senderNewDTO") String senderNewDTO,
			@RequestParam (value = "logo", required = false) MultipartFile logo,
			@RequestParam(value = "certificado", required = false) MultipartFile certificado);

	@GetMapping("/senders/{ruc}/findId")
	 ResponseEntity<Long> findIdByRuc(@PathVariable String ruc);
}
