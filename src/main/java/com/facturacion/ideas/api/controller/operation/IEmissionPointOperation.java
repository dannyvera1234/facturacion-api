package com.facturacion.ideas.api.controller.operation;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.facturacion.ideas.api.controllers.SubsidiaryRestController;
import com.facturacion.ideas.api.dto.EmissionPointNewDTO;
import com.facturacion.ideas.api.dto.EmissionPointResponseDTO;
import com.facturacion.ideas.api.entities.EmissionPoint;
import com.facturacion.ideas.api.entities.Subsidiary;

import javax.validation.Valid;

/**
 * Interface que define los contratos que debe realizar el
 * {@link SubsidiaryRestController}
 * 
 * @author Ronny Chamba
 *
 */
@RequestMapping("/default")
public interface IEmissionPointOperation {

	/**
	 * Inserta un nuevo {@link EmissionPoint} para un {@link Subsidiary} en
	 * particular
	 * 
	 * @param emissionPointNewDTO : El objeto {@link EmissionPoint} a insertar
	 * @return
	 */
	@PreAuthorize(value = "hasRole('ROLE_USER')")
	@PostMapping("/emissions")
	 ResponseEntity<EmissionPointResponseDTO> save(
			 @Valid @RequestBody EmissionPointNewDTO emissionPointNewDTO);
	/**
	 * Recupera todos los {@link EmissionPoint}
	 * 
	 * @param codigo : Codigo del {@link Subsidiary}
	 * @return
	 */
	@GetMapping("subsidiarys/{id}/emissions")
	 ResponseEntity<List<EmissionPointResponseDTO>> findAll(@PathVariable(name = "id") Long codigo);

	@GetMapping("/emissions")
	ResponseEntity<List<EmissionPointResponseDTO>> findAllPoint();


	@GetMapping("subsidiarys/{id}/emissions/{code-point}")
	 ResponseEntity<EmissionPointResponseDTO> findByCodeAndSubsidiary(@PathVariable(name = "id") Long codigo,
			@PathVariable(name = "code-point") String codePoint);

	/**
	 * Recupera un unico {@link EmissionPoint} de un {@link Subsidiary}
	 * 
	 * @param id     : Id del {@link EmissionPoint} a recuperar
	 * @return
	 */
	@GetMapping("/emissions/{id}")
	 ResponseEntity<EmissionPointResponseDTO> findById(@PathVariable Long id);

	/**
	 * Eliminar un {@link EmissionPoint} de un {@link Subsidiary}
	 * 
	 * @param id     : Id del {@link EmissionPoint} a eliminar
	 * @return
	 */

	@PreAuthorize(value = "hasRole('ROLE_USER')")
	@DeleteMapping("/emissions/{id}")
	 ResponseEntity<String> deleteById(@PathVariable Long id);

	/***
	 * Actualizar un {@link EmissionPoint}
	 * 
	 * @param emissionPointNewDTO : Los nuevos datos
	 * @param id            : Id del {@link EmissionPoint} a actualizar
	 * @return
	 */
	@PreAuthorize(value = "hasRole('ROLE_USER')")
	@PutMapping("/emissions/{id}")
	 ResponseEntity<EmissionPointResponseDTO> update(@RequestBody EmissionPointNewDTO emissionPointNewDTO,
			@PathVariable Long id);

}
