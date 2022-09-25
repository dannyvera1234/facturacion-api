package com.facturacion.ideas.api.controller.operation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.facturacion.ideas.api.controllers.SubsidiaryRestController;
import com.facturacion.ideas.api.entities.EmissionPoint;
import com.facturacion.ideas.api.entities.Subsidiary;

/**
 * Interface que define los contratos que debe realizar el {@link SubsidiaryRestController}
 * @author Ronny Chamba
 *
 */
@RequestMapping("/default")
public interface IEmissionPointOperation {

	/**
	 * Inserta un nuevo {@link EmissionPoint} para un {@link Subsidiary} en particular
	 * @param codigo : Id de  {@link Subsidiary}
	 * @param emissionPoint : El objeto {@link EmissionPoint} a insertar
	 * @return 
	 */
	@PostMapping
	public ResponseEntity<?> save(@PathVariable Long codigo, @RequestBody EmissionPoint emissionPoint);

	/**
	 * Recupera todos los {@link EmissionPoint}
	 * @param codigo : Codigo del {@link Subsidiary}
	 * @return
	 */
	@GetMapping
	public ResponseEntity<?> findAll(@PathVariable Long codigo);

	/**
	 * Recupera un unico {@link EmissionPoint} de un {@link Subsidiary}
	 * @param codigo : Id del {@link Subsidiary}
	 * @param id : Id del {@link EmissionPoint} a recuperar
	 * @return
	 */
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable Long codigo, @PathVariable Long id);

	/**
	 * Eliminar un {@link EmissionPoint} de un {@link Subsidiary}
	 * @param codigo : Id del {@link Subsidiary}
	 * @param id : Id del {@link EmissionPoint} a eliminar
	 * @return
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Long codigo, @PathVariable Long id);

	/***
	 * Actualizar un {@link EmissionPoint}
	 * @param emissionPoint : Los nuevos datos
	 * @param codigo : Id del {@link Subsidiary}
	 * @param id : Id del {@link EmissionPoint} a actualizar
	 * @return
	 */
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@RequestBody EmissionPoint emissionPoint, @PathVariable Long codigo,
			@PathVariable Long id);

}
