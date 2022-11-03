package com.facturacion.ideas.api.services;

import java.text.ParseException;
import java.util.List;

import com.facturacion.ideas.api.exeption.BadRequestException;
import com.facturacion.ideas.api.exeption.DuplicatedResourceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.facturacion.ideas.api.admin.AdminEmissionPoint;
import com.facturacion.ideas.api.dto.EmissionPointNewDTO;
import com.facturacion.ideas.api.dto.EmissionPointResponseDTO;
import com.facturacion.ideas.api.entities.CodeDocument;
import com.facturacion.ideas.api.entities.Count;
import com.facturacion.ideas.api.entities.EmissionPoint;
import com.facturacion.ideas.api.entities.Subsidiary;
import com.facturacion.ideas.api.exeption.NotDataAccessException;
import com.facturacion.ideas.api.exeption.NotFoundException;
import com.facturacion.ideas.api.mapper.IEmissionPointMapper;
import com.facturacion.ideas.api.repositories.ICodeDocumentRepository;
import com.facturacion.ideas.api.repositories.IEmissionPointRepository;
import com.facturacion.ideas.api.repositories.IEmployeeRepository;
import com.facturacion.ideas.api.repositories.ISubsidiaryRepository;
import com.facturacion.ideas.api.util.ConstanteUtil;

@Service
public class EmissionPointServiceImpl implements IEmissionPointService {

    private static final Logger LOGGER = LogManager.getLogger(SenderServiceImpl.class);
    @Autowired
    private IEmissionPointRepository emissionPointRepository;

    @Autowired
    private ISubsidiaryRepository subsidiaryRepository;

    @Autowired
    private ICodeDocumentRepository codeDocumentRepository;

    @Autowired
    private IEmissionPointMapper emissionPointMapper;

    @Autowired
    private IEmployeeRepository employeeRepository;

    @Override
    @Transactional
    public EmissionPointResponseDTO save(EmissionPointNewDTO emissionPointNewDTO, Long idSubsidiary) {

        try {

            if (emissionPointNewDTO.getCodePoint() == null) {
                throw new BadRequestException("El codigo de punto emision no puede ser vacio");
            }

            if (emissionPointNewDTO.getCodePoint().matches("[0-9]{3}")) {

                Subsidiary subsidiary = subsidiaryRepository.findById(idSubsidiary).orElseThrow(() -> new NotFoundException(
                        "Establecimiento ide: " + idSubsidiary + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION));

                // Validar si el nuevo punto de emision ya esta registrado en el establecimiento

                if (!emissionPointRepository.existsByCodePointAndSubsidiaryIde(emissionPointNewDTO.getCodePoint(),
                        subsidiary.getIde())) {
                    String codeSubsidiary = subsidiary.getCode();

                    // Crear el EmissionPoint
                    EmissionPoint emissionPoint = AdminEmissionPoint.create(emissionPointNewDTO.getCodePoint());
                    emissionPoint.setStatus(emissionPointNewDTO.isStatus());
                    emissionPoint.setKeyPoint(codeSubsidiary + "-" + emissionPoint.getCodePoint());

                    emissionPoint.setSubsidiary(subsidiary);

                    // Aqui consultar el empleado y si existe agregarlo al punto emision
                    Long idEmpleado = emissionPointNewDTO.getIdEmployee();

                    if (idEmpleado != null) {
                        // Asignar empelado al punto emision
                        emissionPoint.setEmployee(employeeRepository.findById(idEmpleado)
                                .orElseThrow(() -> new NotFoundException("Empleado id: " + idEmpleado + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION)));
                    }

                    EmissionPoint emissionPointSaved = emissionPointRepository.save(emissionPoint);
                    return emissionPointMapper.mapperToDTO(emissionPointSaved);
                }
                throw new DuplicatedResourceException("Punto emisión " + emissionPointNewDTO.getCodePoint() + " ya esta registradoe en el establecimiento " + subsidiary.getCode());


            }

            throw new BadRequestException("El formato de punto emision " + emissionPointNewDTO.getCodePoint() + " es incorrecto, deber ser ejemplo: 009");


        } catch (DataAccessException e) {

            LOGGER.error("Error guardar punto emision: ", e);
            throw new NotDataAccessException("Error guardar ounto emision: " + e.getMessage());

        }

    }

    @Override
    @Transactional
    public void deleteById(Long id) {

        try {

            EmissionPoint emissionPoint = emissionPointRepository.findById(id).orElseThrow(

                    () -> new NotFoundException(
                            "Id  punto emision: " + id + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION));
            emissionPointRepository.deleteById(emissionPoint.getIde());

            // Plz que no se elimina ninguna dato de
        } catch (DataAccessException e) {
            LOGGER.error("Error eliminar punttos emission", e);
            throw new NotDataAccessException("Error eliminar punto emmision" + e.getMessage());
        }

    }

    @Override
    @Transactional(readOnly = true)
    public List<EmissionPointResponseDTO> listAll(Long idSubsidiary) {

        try {
            List<EmissionPoint> emissionPoints = emissionPointRepository.findALlBySubsidiaryIde(idSubsidiary);
            return emissionPointMapper.mapperToDTO(emissionPoints);

        } catch (DataAccessException e) {

            LOGGER.error("Error listar punttos emission esttableccimiento", e);
            throw new NotDataAccessException("Error lisstar puntoss emmiion establecimientto: " + e.getMessage());
        }

    }

    @Override
    @Transactional(readOnly = true)
    public EmissionPointResponseDTO findByCodeAndSubsidiary(String code, Long idSubsidiary) {

        try {
            Subsidiary subsidiary = subsidiaryRepository.findById(idSubsidiary).orElseThrow(() -> new NotFoundException(
                    "Id esablecimiento: " + idSubsidiary + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION));

            EmissionPoint emissionPoint = emissionPointRepository.findByCodePointAndSubsidiary(code, subsidiary);

            return emissionPointMapper.mapperToDTO(emissionPoint);

        } catch (DataAccessException e) {

            LOGGER.error("Error buscar puntos emission esttableccimiento", e);
            throw new NotDataAccessException("Error buscaar puntos emmission establecimientto: " + e.getMessage());
        }

    }

    @Override
    @Transactional(readOnly = true)
    public EmissionPointResponseDTO findById(Long ide) {
        try {

            EmissionPoint emissionPoint = emissionPointRepository.findById(ide).orElseThrow(

                    () -> new NotFoundException(
                            "Id  punto emision: " + ide + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION));
            ;

            return emissionPointMapper.mapperToDTO(emissionPoint);

        } catch (DataAccessException e) {

            LOGGER.error("Error buscar puntos emission", e);
            throw new NotDataAccessException("Error buscaar puntos emmission: " + e.getMessage());
        }

    }

    @Override
    public EmissionPointResponseDTO update(EmissionPointNewDTO emissionPointNewDTO, Long id) {

        try {

            EmissionPoint emissionPoint = emissionPointRepository.findById(id).orElseThrow(

                    () -> new NotFoundException(
                            "Id  punto emision: " + id + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION));

            // Del punto emsion solo actualizamos el estado

            emissionPoint.setStatus(emissionPointNewDTO.isStatus());

            EmissionPoint emissionPointUpdated = emissionPointRepository.save(emissionPoint);

            return emissionPointMapper.mapperToDTO(emissionPointUpdated);

        } catch (DataAccessException e) {

            LOGGER.error("Error actualizar punto emission", e);
            throw new NotDataAccessException("Error actualizar punto emmission: " + e.getMessage());
        }

    }

}
