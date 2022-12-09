package com.facturacion.ideas.api.services;

import java.text.ParseException;
import java.util.List;

import com.facturacion.ideas.api.dto.CountNewDTO;
import com.facturacion.ideas.api.entities.*;
import com.facturacion.ideas.api.enums.RolEnum;
import com.facturacion.ideas.api.exeption.BadRequestException;
import com.facturacion.ideas.api.exeption.DuplicatedResourceException;
import com.facturacion.ideas.api.repositories.*;
import com.facturacion.ideas.api.security.enums.RolNombreEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.facturacion.ideas.api.admin.AdminEmissionPoint;
import com.facturacion.ideas.api.dto.EmissionPointNewDTO;
import com.facturacion.ideas.api.dto.EmissionPointResponseDTO;
import com.facturacion.ideas.api.exeption.NotDataAccessException;
import com.facturacion.ideas.api.exeption.NotFoundException;
import com.facturacion.ideas.api.mapper.IEmissionPointMapper;
import com.facturacion.ideas.api.util.ConstanteUtil;

@Service
public class EmissionPointServiceImpl implements IEmissionPointService {

    private static final Logger LOGGER = LogManager.getLogger(SenderServiceImpl.class);
    @Autowired
    private IEmissionPointRepository emissionPointRepository;

    @Autowired
    private ISubsidiaryRepository subsidiaryRepository;

    @Autowired
    private IEmissionPointMapper emissionPointMapper;

    @Autowired
    private IEmployeeRepository employeeRepository;

    @Autowired
    private ISenderRepository senderRepository;

    @Override
    @Transactional
    public EmissionPointResponseDTO save(EmissionPointNewDTO emissionPointNewDTO) {

        try {

            Long idSender = senderRepository.findIdByRuc(ConstanteUtil.TOKEN_USER).orElseThrow(
                    () -> new NotFoundException(String.format("Emisor %s %s", ConstanteUtil.TOKEN_USER, ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION))
            );


            Subsidiary subsidiary = subsidiaryRepository.findBySenderIde(idSender).orElseThrow(
                    () -> new NotFoundException("No existe un establecimiento registrado actual")
            );

            if (emissionPointNewDTO.getCodePoint().matches("[0-9]{3}")) {


                // Validar si el nuevo punto de emision ya esta registrado en el establecimiento
                if (!emissionPointRepository.existsByCodePointAndSubsidiaryIde(emissionPointNewDTO.getCodePoint(),
                        subsidiary.getIde())) {

                    // Crear el EmissionPoint
                    EmissionPoint emissionPoint = AdminEmissionPoint.create(emissionPointNewDTO.getCodePoint());
                    emissionPoint.setStatus(emissionPointNewDTO.isStatus());
                    emissionPoint.setKeyPoint(emissionPointNewDTO.getKeyPoint());
                    emissionPoint.setSubsidiary(subsidiary);


                    // Aqui consultar el empleado y si existe agregarlo al punto emision
                    Long idEmpleado = emissionPointNewDTO.getIdEmployee();

                    if (idEmpleado != null) {
                        Employee employee = employeeRepository.findById(idEmpleado).orElseThrow(() ->
                                new NotFoundException(String.format("Empleado %s %s", idEmpleado, ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION))
                        );
                        emissionPoint.setEmployee(employee);
                    }

                    EmissionPoint emissionPointSaved = emissionPointRepository.save(emissionPoint);
                    return emissionPointMapper.mapperToDTO(emissionPointSaved);
                }
                throw new DuplicatedResourceException("Punto emisión " + emissionPointNewDTO.getCodePoint() + " ya esta registrado en el establecimiento " + subsidiary.getCode());

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
    public List<EmissionPointResponseDTO> listAll() {

        try {

            Long idSender = senderRepository.findIdByRuc(ConstanteUtil.TOKEN_USER).orElseThrow(
                    () -> new NotFoundException(String.format("Emisor %s %s", ConstanteUtil.TOKEN_USER, ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION))
            );

            /*Long idSubsidiary = subsidiaryRepository.findIdBySender(idSender).orElseThrow(
                    () -> new NotFoundException("No existe un establecimiento registrado actual")
            );*/

            Subsidiary subsidiary = subsidiaryRepository.fetchSubsidiaryAndEmissionPoints(idSender).orElseThrow(
                    () -> new NotFoundException("No existe un establecimiento registrado actual")
            );

            List<EmissionPoint> emissionPoints = subsidiary.getEmissionPoints();

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


            Long idSender = senderRepository.findIdByRuc(ConstanteUtil.TOKEN_USER).orElseThrow(
                    () -> new NotFoundException(String.format("Emisor %s %s", ConstanteUtil.TOKEN_USER, ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION))
            );

            EmissionPoint emissionPoint = emissionPointRepository.findById(id).orElseThrow(

                    () -> new NotFoundException(
                            "Punto emision: " + id + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION));

            // Del punto emsion solo actualizamos el estado

            emissionPoint.setStatus(emissionPointNewDTO.isStatus());

            if (emissionPointNewDTO.getKeyPoint() != null) {
                emissionPoint.setKeyPoint(emissionPointNewDTO.getKeyPoint());
            }

            if (emissionPointNewDTO.getIdEmployee() == null) {
                emissionPoint.setEmployee(null);

            } else {

                Employee employee = employeeRepository.findById(emissionPointNewDTO.getIdEmployee())
                        .orElseThrow(() -> new NotFoundException(String.format("Empleado %s %s",
                                emissionPointNewDTO.getIdEmployee(), ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION)));

                emissionPoint.setEmployee(employee);
            }

            EmissionPoint emissionPointUpdated = emissionPointRepository.save(emissionPoint);

            return emissionPointMapper.mapperToDTO(emissionPointUpdated);

        } catch (DataAccessException e) {

            LOGGER.error("Error actualizar punto emission", e);
            throw new NotDataAccessException("Error actualizar punto emmission: " + e.getMessage());
        }

    }

}
