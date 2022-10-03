package com.facturacion.ideas.api.controller.operation;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.facturacion.ideas.api.dto.SubsidiaryNewDTO;
import com.facturacion.ideas.api.dto.SubsidiaryResponseDTO;

public interface ISubsidiaryOperation {

	@PostMapping("/senders/{id}/subsidiarys")
	public ResponseEntity<SubsidiaryResponseDTO> save(@RequestBody SubsidiaryNewDTO subsidiaryNewDTO,
			@PathVariable("id") Long idSender);

	@GetMapping("/senders/{id}/subsidiarys")
	public ResponseEntity<List<SubsidiaryResponseDTO>> findAll(@PathVariable("id") Long idSender);

	/**
	 * Busca un establecimiento mediante su code dentro de un emisor en particular
	 * @param idSender : ide establecimiento
	 * @param code : codigo de establecimiento
	 * @return
	 */
	@GetMapping("/senders/{id}/subsidiarys/search")
	public ResponseEntity<List<SubsidiaryResponseDTO>> findByCodeAndSender(@PathVariable("id") Long idSender,
			@RequestParam(name = "code", defaultValue = "") String code);

	@GetMapping("/subsidiarys/{id}")
	public ResponseEntity<SubsidiaryResponseDTO> findById(@PathVariable Long id);

	@PutMapping("/subsidiarys/{id}")
	public ResponseEntity<SubsidiaryResponseDTO> update(@RequestBody SubsidiaryNewDTO subsidiaryNewDTO,
			 @PathVariable Long id);

	@DeleteMapping("/subsidiarys/{id}")
	public ResponseEntity<String> deleteById(@PathVariable Long id);

}
