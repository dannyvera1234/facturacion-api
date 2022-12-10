package com.facturacion.ideas.api.controllers;

import java.util.List;
import java.util.Set;


import com.facturacion.ideas.api.dto.SubsidiaryAndEmissionPointDTO;
import com.facturacion.ideas.api.exeption.EncryptedException;
import com.facturacion.ideas.api.validation.SenderCustomerEditor;
import com.google.gson.Gson;
import com.google.gson.stream.MalformedJsonException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import com.facturacion.ideas.api.controller.operation.ISenderOperation;
import com.facturacion.ideas.api.dto.SenderNewDTO;
import com.facturacion.ideas.api.dto.SenderResponseDTO;
import com.facturacion.ideas.api.entities.Sender;
import com.facturacion.ideas.api.exeption.NotDataAccessException;
import com.facturacion.ideas.api.services.ISenderService;
import com.facturacion.ideas.api.util.ConstanteUtil;
import org.springframework.web.multipart.MultipartFile;

/**
 * RestController que expone servicios web para la entidad {@link Sender}
 *
 * @author Ronny Chamba
 */

@CrossOrigin(origins = ConstanteUtil.CROOS_ORIGIN)
@RestController
@RequestMapping("/facturacion")
public class SenderRestController implements ISenderOperation {

    private static final Logger LOGGER = LogManager.getLogger(SenderRestController.class);

    @Autowired
    private ISenderService senderService;

    @Autowired
    private SenderCustomerEditor senderEditor;

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {

        String nombre = webDataBinder.getObjectName();
        System.out.println("HOla nundo: " + nombre);
        //webDataBinder.registerCustomEditor(SenderNewDTO.class, "senderNewDTO", senderEditor);


    }

    @Override
    public ResponseEntity<SenderResponseDTO> save(String senderNewDTO, MultipartFile logo,
                                                  MultipartFile certificado) {
        LOGGER.info(String.format("Emisor guardar: %s", senderNewDTO));

        LOGGER.debug("Estado certificado: " +  certificado.isEmpty());
        LOGGER.debug("Estado logo: " +  (logo == null? "Logo No seleccionado": "Logo seleccionado"));

        try {
            SenderNewDTO senderConvert = new Gson().fromJson(senderNewDTO, SenderNewDTO.class);

            SenderResponseDTO senderResponseDTO = senderService.save(senderConvert,logo, certificado);

            //SenderResponseDTO senderResponseDTO = new SenderResponseDTO();
            return new ResponseEntity<>(senderResponseDTO, HttpStatus.CREATED);

        } catch (NotDataAccessException e) {
            throw new NotDataAccessException(e.getMessage());
        } catch (EncryptedException e) {
            throw new EncryptedException(e.getMessage());
        }

    }


    @Override
    public ResponseEntity<SenderResponseDTO> findById(Long id) {

        try {

            SenderResponseDTO senderResponseDTO = senderService.findById(id);

            return ResponseEntity.ok(senderResponseDTO);

        } catch (NotDataAccessException e) {

            throw new NotDataAccessException(e.getMessage());
        }

    }

    @Override
    public ResponseEntity<SenderNewDTO> findToEdit() {

        try {
            SenderNewDTO senderResponseDTO = senderService.findToEdit();
            return ResponseEntity.ok(senderResponseDTO);

        } catch (NotDataAccessException e) {

            throw new NotDataAccessException(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<SenderResponseDTO> update(SenderNewDTO senderNewDTO, Long id) {

        LOGGER.info("Id Emisor: " + id);

        try {


            SenderResponseDTO senderResponseDTO = senderService.update(senderNewDTO, id);

            return ResponseEntity.ok(senderResponseDTO);


        } catch (NotDataAccessException e) {
            throw new NotDataAccessException(e.getMessage());

        }
    }

    @Override
    public ResponseEntity<SenderResponseDTO> findByRuc(String ruc) {
        try {

            SenderResponseDTO senderResponseDTO = senderService.findByRuc(ruc);

            return ResponseEntity.ok(senderResponseDTO);

        } catch (NotDataAccessException e) {

            throw new NotDataAccessException(e.getMessage());
        }

    }


   @Override
    public ResponseEntity<Long> findIdByRuc( String ruc) {
        try {
            LOGGER.info("ruc recibido: " + ruc);

            Long idSender = senderService.findIdByRuc(ruc);
            return ResponseEntity.ok(idSender);
        } catch (NotDataAccessException e) {

            throw new NotDataAccessException(e.getMessage());
        }

    }

}
