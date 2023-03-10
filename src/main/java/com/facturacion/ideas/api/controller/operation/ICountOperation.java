package com.facturacion.ideas.api.controller.operation;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.facturacion.ideas.api.controllers.CountRestController;
import com.facturacion.ideas.api.dto.CountNewDTO;
import com.facturacion.ideas.api.dto.CountResponseDTO;
import com.facturacion.ideas.api.dto.DetailsAgreementDTO;
import com.facturacion.ideas.api.dto.LoginDTO;
import com.facturacion.ideas.api.entities.Agreement;
import com.facturacion.ideas.api.entities.Count;
import com.facturacion.ideas.api.entities.DetailsAggrement;
import com.facturacion.ideas.api.entities.Login;
import com.facturacion.ideas.api.util.FunctionUtil;

/**
 * Interface que define los contratos que debe realizar el
 * {@link CountRestController}
 *
 * @author Ronny Chamba
 */
@RequestMapping("/default")
public interface ICountOperation {


    /**
     * Busca una {@link Count} a traves de su id
     *
     * @param id : Id de Count
     * @return Respuesta
     * {@link FunctionUtil#getResponseEntity(HttpStatus, Object, String)}
     */
    @GetMapping("/{id}")
    ResponseEntity<CountResponseDTO> findById(@PathVariable(required = false) Long id);

    @GetMapping("/search")
    ResponseEntity<CountResponseDTO> findByRuc(@RequestParam(required = false, defaultValue = "") String ruc);


    /**
     * Actualiza una {@link Count}
     *
     * @param count : Una {@link Count} con los nuevos datos
     * @param id    : Id de la Count a actualizar
     * @return Respuesta
     * {@link FunctionUtil#getResponseEntity(HttpStatus, Object, String)}
     * con los nuevos datos actualizados
     */
    @PutMapping("/{id}")
    ResponseEntity<CountResponseDTO> update(@RequestBody CountNewDTO count, @PathVariable Long id);


    /**
     * Inserta un nuevo registro de {@link Login} en la Base de datos
     *
     * @param idCount : Id de la Cuenta
     * @return {@link FunctionUtil#getResponseEntity(HttpStatus, Object, String)}
     */
    @PostMapping("/{id}/logins")
    ResponseEntity<LoginDTO> saveLogin(@PathVariable("id") Long idCount);


    @GetMapping("/{id}/logins")
    ResponseEntity<List<LoginDTO>> findAllLogin(@PathVariable("id") Long idCount);

    @GetMapping("/{ruc}/agrement-details")
    ResponseEntity<Map<String, Object>> listAgreementDetailsAllByRuc(@PathVariable String ruc);
}
