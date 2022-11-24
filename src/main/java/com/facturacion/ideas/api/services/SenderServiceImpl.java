package com.facturacion.ideas.api.services;

import java.util.List;

import com.facturacion.ideas.api.admin.AdminEmissionPoint;
import com.facturacion.ideas.api.entities.*;
import com.facturacion.ideas.api.enums.TypeFileEnum;
import com.facturacion.ideas.api.exeption.*;
import com.facturacion.ideas.api.util.PathDocuments;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.facturacion.ideas.api.admin.AdminSender;
import com.facturacion.ideas.api.admin.AdminSubsidiary;
import com.facturacion.ideas.api.dto.SenderNewDTO;
import com.facturacion.ideas.api.dto.SenderResponseDTO;
import com.facturacion.ideas.api.mapper.ISenderMapper;
import com.facturacion.ideas.api.repositories.ICountRepository;
import com.facturacion.ideas.api.repositories.ISenderRepository;
import com.facturacion.ideas.api.util.ConstanteUtil;
import org.springframework.web.multipart.MultipartFile;

@Service
public class SenderServiceImpl implements ISenderService {

    private static final Logger LOGGER = LogManager.getLogger(SenderServiceImpl.class);

    @Autowired
    private ISenderRepository senderRepository;

    @Autowired
    private ICountRepository countRepository;

    @Autowired
    private ISenderMapper senderMapper;

    @Autowired
    private IUploadFileService uploadFileService;


    @Autowired
    private IEncryptionService encryptionService;

    @Override
    @Transactional
    public SenderResponseDTO save(Long idCount, final SenderNewDTO senderNewDTO,
                                  MultipartFile fileImg, MultipartFile fileCertificate) {

        try {

            Count count = countRepository.findById(idCount).orElseThrow(() -> new NotFoundException(
                    "Cuenta " + idCount + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION));

            // Setar el ruc de la cuenta
            senderNewDTO.setRuc(count.getRuc());

            // Verificar si ya existe un emisor con el Ruc
            if (!senderRepository.existsByRuc(senderNewDTO.getRuc())) {

                // Encryptar password certificado
                senderNewDTO.setPasswordCerticate(encryptionService.encrypt(senderNewDTO.getPasswordCerticate()));

                Sender sender = senderMapper.mapperToEntity(senderNewDTO);

                String newSubsidiary = senderNewDTO.getSubsidiary();
                String emisionPoint = senderNewDTO.getEmisionPoint();

                // Quiere decir que envio un establecimiento y punto emision a guardar
                if (newSubsidiary != null && emisionPoint != null) {

                    if (AdminSubsidiary.isValidFormat(newSubsidiary + "-" + emisionPoint)) {

                        Subsidiary subsidiary = AdminSubsidiary.create(senderNewDTO, newSubsidiary);
                        EmissionPoint emissionPoint = AdminEmissionPoint.create(emisionPoint);

                        emissionPoint.setStatus(true);
                        emissionPoint.setKeyPoint(subsidiary.getCode() + "-" + emissionPoint.getCodePoint());

                        subsidiary.addEmissionPoint(emissionPoint);
                        sender.addSubsidiary(subsidiary);

                        // Agrega Cuenta
                        sender.setCount(count);

                        // Asignar logo
                        String nameFile = uploadFileService.saveFile(fileImg, PathDocuments.PATH_BASE.concat(sender.getRuc()), TypeFileEnum.IMG);
                        sender.setLogo(nameFile);


                        // Asignar certificado
                        String nameFileCerti = uploadFileService.saveFile(fileCertificate, PathDocuments.PATH_BASE.concat(sender.getRuc()), TypeFileEnum.FILE);
                        sender.setNameCerticate(nameFileCerti);

                        return senderMapper.mapperToDTO(senderRepository.save(sender));
                    }
                    throw new BadRequestException("Formato del establecimiento  " + newSubsidiary + " o punto emision " + " son  incorrectos");
                }
                throw new BadRequestException("Establecimiento " + newSubsidiary + " o punto emsion " + emisionPoint + " no pueder estar vacios");
            }

            throw new DuplicatedResourceException(
                    "Ruc " + senderNewDTO.getRuc() + ConstanteUtil.MESSAJE_DUPLICATED_RESOURCE_DEFAULT_EXCEPTION);

        } catch (EncryptedException e) {

            // EL mensaje viene desde el encriptar password del certificado
            LOGGER.info(e.getMessage(), e);
            throw new NotDataAccessException(e.getMessage());
        } catch (DataAccessException e) {

            LOGGER.info("Error guardar emisor", e);
            throw new NotDataAccessException("Error guardar emisor: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public SenderResponseDTO findByRuc(String ruc) {

        try {

            Sender sender = senderRepository.findByRuc(ruc).orElseThrow(
                    () -> new NotFoundException("Emisor Ruc  " + ruc + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION));

            return senderMapper.mapperToDTO(sender);

        } catch (DataAccessException e) {
            LOGGER.info("Error buscar emisor ruc", e);
            throw new NotDataAccessException("Error buscar ruc: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public SenderResponseDTO findById(Long id) {

        try {

            Sender sender = senderRepository.findById(id).orElseThrow(
                    () -> new NotFoundException("id: " + id + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION));

            return senderMapper.mapperToDTO(sender);

        } catch (DataAccessException e) {
            LOGGER.info("Error buscar emisor", e);
            throw new NotDataAccessException("Error buscar emisor: " + e.getMessage());
        }

    }

    @Override
    @Transactional(readOnly = true)
    public List<SenderResponseDTO> findAll() {

        try {

            List<Sender> senders = senderRepository.findAll();

            return senderMapper.mapperToDTO(senders);

        } catch (DataAccessException e) {
            LOGGER.info("Error listar emisores", e);
            throw new NotDataAccessException("Error listar emisores: " + e.getMessage());
        }

    }

    @Override
    @Transactional
    public SenderResponseDTO update(SenderNewDTO senderNewDTO, Long idSender) {

        try {

            Sender sender = senderRepository.findById(idSender).orElseThrow(
                    () -> new NotFoundException("id: " + idSender + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION));

            AdminSender.update(sender, senderNewDTO);

            Sender senderUpdated = senderRepository.save(sender);

            return senderMapper.mapperToDTO(senderUpdated);

        } catch (DataAccessException e) {
            LOGGER.info("Error actualizar emisor", e);
            throw new NotDataAccessException("Error actualizar emisor: " + e.getMessage());
        }

    }

}
