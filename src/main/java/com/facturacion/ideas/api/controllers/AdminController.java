package com.facturacion.ideas.api.controllers;

import com.facturacion.ideas.api.dto.AgreementDTO;
import com.facturacion.ideas.api.dto.CountNewDTO;
import com.facturacion.ideas.api.dto.CountResponseDTO;
import com.facturacion.ideas.api.dto.SenderResponseDTO;
import com.facturacion.ideas.api.entities.Sender;
import com.facturacion.ideas.api.exeption.NotDataAccessException;
import com.facturacion.ideas.api.services.IAgreementService;
import com.facturacion.ideas.api.services.ICountService;
import com.facturacion.ideas.api.services.ISenderService;
import com.facturacion.ideas.api.util.ConstanteUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = ConstanteUtil.CROOS_ORIGIN)
@RestController
@RequestMapping("/facturacion/admin")
public class AdminController {

    private static final Logger LOGGER = LogManager.getLogger(AdminController.class);

    @Autowired
    private ICountService countService;

    @Autowired
    private ISenderService senderService;

    @Autowired
    private IAgreementService agreementService;

    @PostMapping("/counts")
    public ResponseEntity<CountResponseDTO> saveCount(@RequestBody CountNewDTO countNewDTO) {

        LOGGER.info("Cuenta a guardar: " + countNewDTO);

        try {
            CountResponseDTO countResponseDTO = countService.saveCount(countNewDTO);
            return new ResponseEntity<>(countResponseDTO, HttpStatus.CREATED);

        } catch (NotDataAccessException e) {
            throw new NotDataAccessException(e.getMessage());

        }
    }

    @GetMapping("/counts")
    public ResponseEntity<Map<String, Object>> findAllCountWithAgreement() {

        try {
            List<CountResponseDTO> countResponseDTOs = countService.fetchByWithAgreement();

            Map<String, Object> dataResponse = new HashMap<>();
            dataResponse.put("data", countResponseDTOs);
            dataResponse.put("size", countResponseDTOs.size());
            return ResponseEntity.ok(dataResponse);

        } catch (NotDataAccessException e) {

            throw new NotDataAccessException(e.getMessage());
        }

    }

    @PutMapping("/counts/{id}/status")
    public ResponseEntity<CountResponseDTO> updateStatusCount(@PathVariable Long id) {

        try {

            CountResponseDTO countResponseDTO = countService.updateCountStatus(id);

            return ResponseEntity.ok(countResponseDTO);

        } catch (NotDataAccessException e) {

            throw new NotDataAccessException(e.getMessage());
        }

    }

    @DeleteMapping("/counts/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {

        LOGGER.info("Id Cuenta a eliminar: " + id);
        try {

            countService.deleteCountById(id);
            return ResponseEntity.noContent().build();

        } catch (NotDataAccessException e) {

            throw new NotDataAccessException(e.getMessage());

        }

    }

    @GetMapping("/senders")
    public ResponseEntity< Map<String, Object>> listSenderAll() {

        try {

            List<SenderResponseDTO> senderResponseDTOs = senderService.findAll();
            Map<String, Object> mapaData= new HashMap<>();
            mapaData.put("data", senderResponseDTOs);
            mapaData.put("size", senderResponseDTOs.size());
            return ResponseEntity.ok(mapaData);


        } catch (NotDataAccessException e) {
            throw new NotDataAccessException(e.getMessage());

        }
    }

    @PostMapping("/agreements")
    public ResponseEntity<AgreementDTO> saveAgreement(@RequestBody AgreementDTO agreementDTO){

        LOGGER.info("Plan a guardar " + agreementDTO);
        try {

            AgreementDTO agreementSaved = agreementService.save(agreementDTO);

            return  new ResponseEntity<>(agreementSaved, HttpStatus.CREATED);

        } catch (NotDataAccessException e) {

            throw new NotDataAccessException(e.getMessage());
        }

    }


  @GetMapping("/agreements")
    public ResponseEntity<  Map<String, Object>> listAgreementAll() {

        try {

            List<AgreementDTO> listAgreement = agreementService.listAll();
            Map<String, Object> mapaData = new HashMap<>();
            mapaData.put("data", listAgreement);
            mapaData.put("size", listAgreement.size());

            return ResponseEntity.ok(mapaData);

        } catch (NotDataAccessException e) {

            throw new NotDataAccessException(e.getMessage());

        }

    }

    @GetMapping("/agreements/{id}")
    public ResponseEntity<AgreementDTO> findAgreemetById(Long id) {

        LOGGER.info("Id Plan a buscar: " + id);

        try {

            AgreementDTO agreementDTO = agreementService.findById(id);

            return ResponseEntity.ok(agreementDTO);
        } catch (NotDataAccessException e) {

            throw new NotDataAccessException(e.getMessage());

        }

    }




}

