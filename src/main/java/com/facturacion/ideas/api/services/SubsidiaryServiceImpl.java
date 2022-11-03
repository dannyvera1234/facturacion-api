package com.facturacion.ideas.api.services;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import com.facturacion.ideas.api.dto.SenderNewDTO;
import com.facturacion.ideas.api.dto.SubsidiaryAndEmissionPointDTO;
import com.facturacion.ideas.api.entities.*;
import com.facturacion.ideas.api.exeption.BadRequestException;
import com.facturacion.ideas.api.exeption.DuplicatedResourceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.facturacion.ideas.api.admin.AdminCodeDocument;
import com.facturacion.ideas.api.admin.AdminEmissionPoint;
import com.facturacion.ideas.api.admin.AdminSubsidiary;
import com.facturacion.ideas.api.dto.SubsidiaryNewDTO;
import com.facturacion.ideas.api.dto.SubsidiaryResponseDTO;
import com.facturacion.ideas.api.exeption.NotDataAccessException;
import com.facturacion.ideas.api.exeption.NotFoundException;
import com.facturacion.ideas.api.mapper.ISubsidiaryMapper;
import com.facturacion.ideas.api.repositories.ICodeDocumentRepository;
import com.facturacion.ideas.api.repositories.ISenderRepository;
import com.facturacion.ideas.api.repositories.ISubsidiaryRepository;
import com.facturacion.ideas.api.util.ConstanteUtil;

@Service
public class SubsidiaryServiceImpl implements ISubsidiaryService {

    private static final Logger LOGGER = LogManager.getLogger(SubsidiaryServiceImpl.class);

    @Autowired
    private ISubsidiaryRepository subsidiaryRepository;

    @Autowired
    private ISenderRepository senderRepository;

    @Autowired
    private ICodeDocumentRepository codeDocumentRepository;

    @Autowired
    private ISubsidiaryMapper subsidiaryMapper;

    @Override
    @Transactional
    public SubsidiaryResponseDTO save(SubsidiaryNewDTO subsidiaryNewDTO, Long idSender) {

        try {

            Sender sender = senderRepository.findById(idSender).orElseThrow(() -> new NotFoundException(
                    "Emisor ide: " + idSender + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION));

            SenderNewDTO senderNewDTO = new SenderNewDTO();

            senderNewDTO.setMatrixAddress((subsidiaryNewDTO.getAddress() != null
                    && !subsidiaryNewDTO.getAddress().isEmpty()) ? subsidiaryNewDTO.getAddress() :
                    sender.getMatrixAddress());

            senderNewDTO.setSocialReason(sender.getSocialReason());
            if (subsidiaryNewDTO.getCode() == null) {
                throw new BadRequestException("El codigo del establecimiento no puede estar vacio");
            }

            if (AdminSubsidiary.isValidFormat(subsidiaryNewDTO.getCode())) {

                String[] data = AdminSubsidiary.numberSubsidiaryAndEmissionPoint(subsidiaryNewDTO.getCode());
                String numberSubsidiary = data[0];
                String numberEmissionPoint = data[1];

                // Verificar si el nuevo establecimiento ya existe para un emisor
                if (!subsidiaryRepository.existsByCodeAndSenderIde(numberSubsidiary, sender.getIde())) {

                    // Crear Establecimiento
                    Subsidiary subsidiary = AdminSubsidiary.create(senderNewDTO, numberSubsidiary);

                    EmissionPoint emissionPoint = AdminEmissionPoint.create(numberEmissionPoint);
                    emissionPoint.setStatus(false);

                    emissionPoint.setKeyPoint(subsidiary.getCode() + "-" + emissionPoint.getCodePoint());
                    subsidiary.addEmissionPoint(emissionPoint);

                    subsidiary.setSender(sender);

                    return subsidiaryMapper.mapperToDTO(subsidiaryRepository.save(subsidiary));

                }else throw  new DuplicatedResourceException("Establecimiento " + numberSubsidiary + ConstanteUtil.MESSAJE_DUPLICATED_RESOURCE_DEFAULT_EXCEPTION);


            } else
                throw new BadRequestException("Formato del establecimiento  " + subsidiaryNewDTO.getCode() + " es incorrecto, debe tener el formato, ejemplo: 001-006");


        } catch (DataAccessException e) {
            LOGGER.error("Error guardar establecimiento: ", e);
            throw new NotDataAccessException("Error guardar establecimiento: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public void deleteById(Long ide) {

        try {
            Subsidiary subsidiary = subsidiaryRepository.findById(ide).orElseThrow(() -> new NotFoundException(
                    "Id establecimiento: " + ide + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION));

            // Eliminar El establecimiento y por la cascada sus Puntos de Emision
            subsidiaryRepository.deleteById(subsidiary.getIde());

            // Eliminar El Registro en CodeDocument del Establecimiento recientiemente
            // eliminado
            Long idCount = subsidiary.getSender().getCount().getIde();
            codeDocumentRepository.deleteByIdCountAndCodeSubsidiary(idCount, subsidiary.getCode());

        } catch (DataAccessException e) {
            LOGGER.error("Error eliminar establecimiento: ", e);
            throw new NotDataAccessException("Error eliminar establecimiento: " + e.getMessage());
        }

    }

    @Override
    @Transactional(readOnly = true)
    public List<SubsidiaryResponseDTO> findAll(Long idSender) {

        try {
            Sender sender = senderRepository.findById(idSender).orElseThrow(() -> new NotFoundException(
                    "Emisor id: " + idSender + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION));

            List<Subsidiary> subsidiaries = sender.getSubsidiarys();

            return subsidiaryMapper.mapperToDTO(subsidiaries);

        } catch (DataAccessException e) {

            LOGGER.error("Error listar establecimientos x Emisor: ", e);
            throw new NotDataAccessException("Error listar establecimiento x Emisor: " + e.getMessage());
        }

    }

    @Override
    @Transactional(readOnly = true)
    public SubsidiaryResponseDTO findById(Long ide) {

        try {

            Subsidiary subsidiary = subsidiaryRepository.findById(ide).orElseThrow(() -> new NotFoundException(
                    "id establecimiento: " + ide + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION));

            return subsidiaryMapper.mapperToDTO(subsidiary);

        } catch (DataAccessException e) {

            LOGGER.error("Error buscar establecimiento: ", e);
            throw new NotDataAccessException("Error buscar establecimiento: " + e.getMessage());
        }

    }

    @Override
    @Transactional(readOnly = true)
    public List<SubsidiaryResponseDTO> findByCodeAndSender(String code, Long idSender) {

        try {

            Sender sender = senderRepository.findById(idSender).orElseThrow(() -> new NotFoundException(
                    "ID emisor: " + idSender + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION));

            List<Subsidiary> subsidiaries = subsidiaryRepository.findByCodeAndSender(code, sender);

            return subsidiaryMapper.mapperToDTO(subsidiaries);

        } catch (DataAccessException e) {

            LOGGER.error("Error actualizar establecimientos: ", e);
            throw new NotDataAccessException("Error actualizar establecimiento: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubsidiaryAndEmissionPointDTO> listSubsidiaryAndEmissionPointDTOByRuc(String ruc) {
        try {
            if (senderRepository.existsByRuc(ruc)) {
                List<Subsidiary> subsidiaries = subsidiaryRepository.fetchSubsidiaryAndEmissionPointsByRuc(ruc);
                return subsidiaryMapper.mapperToDTOAndEmissionPoint(subsidiaries);
            }
            throw new NotFoundException("Emisor con Ruc " + ruc + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION);

        } catch (DataAccessException e) {

            LOGGER.error("Error al consultar establecimiento/ pto emision emisor", e);
            throw new NotDataAccessException("Error al consultar los establecimientos del emisor: " + e.getMessage());
        }

    }

    @Override
    @Transactional
    public SubsidiaryResponseDTO update(SubsidiaryNewDTO subsidiaryNewDTO, Long id) {

        try {
            Subsidiary subsidiary = subsidiaryRepository.findById(id).orElseThrow(() -> new NotFoundException(
                    "id establecimiento: " + id + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION));

            if (subsidiaryNewDTO.getAddress() != null) {
                subsidiary.setAddress(subsidiaryNewDTO.getAddress());

            }

            subsidiary.setStatus(subsidiaryNewDTO.isStatus());

            return subsidiaryMapper.mapperToDTO(subsidiaryRepository.save(subsidiary));
        } catch (DataAccessException e) {
            LOGGER.error("Error listar establecimientos x code: ", e);
            throw new NotDataAccessException("Error listar establecimiento x code: " + e.getMessage());
        }

    }


}
