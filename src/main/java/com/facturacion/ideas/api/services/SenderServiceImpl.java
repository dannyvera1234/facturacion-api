package com.facturacion.ideas.api.services;

import java.util.List;
import java.util.Optional;

import com.facturacion.ideas.api.admin.AdminEmissionPoint;
import com.facturacion.ideas.api.dto.ComprobantesResponseDTO;
import com.facturacion.ideas.api.entities.*;
import com.facturacion.ideas.api.enums.TypeFileEnum;
import com.facturacion.ideas.api.exeption.*;
import com.facturacion.ideas.api.repositories.IEmissionPointRepository;
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


    @Autowired
    private IEmissionPointRepository emissionPointRepository;

    @Override
    @Transactional
    public SenderResponseDTO save(final SenderNewDTO senderNewDTO,
                                  MultipartFile logo, MultipartFile certificado) {

        try {
            Long idCount = countRepository.findIdByRuc(ConstanteUtil.TOKEN_USER)
                    .orElseThrow(() -> new NotFoundException(
                            String.format("Cuenta %s %s", ConstanteUtil.TOKEN_USER, ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION)));

            // Setar el ruc de la cuenta
            senderNewDTO.setRuc(ConstanteUtil.TOKEN_USER);

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
                        sender.setCount(new Count(idCount));

                        // Asignar logo
                        if (logo != null && !logo.isEmpty()) {
                            String nameFile = uploadFileService.saveFile(logo, PathDocuments.PATH_BASE.concat(sender.getRuc()), TypeFileEnum.IMG);
                            sender.setLogo(nameFile);
                        } else sender.setLogo(null);


                        if (certificado == null) {
                            throw new BadRequestException("El Certificado P12 no pueder estar vacio");
                        }

                        // Asignar certificado
                        String nameFileCerti = uploadFileService.saveFile(certificado, PathDocuments.PATH_BASE.concat(sender.getRuc()), TypeFileEnum.FILE);
                        sender.setNameCerticate(nameFileCerti);

                        return senderMapper.mapperToDTO(senderRepository.save(sender));
                    }
                    throw new BadRequestException("Formato del establecimiento  " + newSubsidiary + " o punto emision " + " son  incorrectos");
                }
                throw new BadRequestException("Establecimiento " + newSubsidiary + " o punto emsion " + emisionPoint + " no pueder estar vacios");
            }

            throw new DuplicatedResourceException(
                    "Emisor " + senderNewDTO.getRuc() + ConstanteUtil.MESSAJE_DUPLICATED_RESOURCE_DEFAULT_EXCEPTION);

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
    public SenderNewDTO findToEdit() {

        try {
            Sender sender = senderRepository.fetchSubsidiaryAndPuntosEmisionEmailByRuc(ConstanteUtil.TOKEN_USER).orElseThrow(
                    () -> new NotFoundException(
                            String.format("Emisor %s %s", ConstanteUtil.TOKEN_USER, ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION)
                    ));


            // Desencriptar la contrasegnia del certificado
            final String claveDescriptada = encryptionService.deEncrypt(sender.getPasswordCerticate());
            sender.setPasswordCerticate(claveDescriptada);

            SenderNewDTO senderNewDTO = senderMapper.mapperToDTOEdit(sender);

            Subsidiary subsidiary = sender.getSubsidiarys().get(0);

            Optional<EmissionPoint> emissionPoint = emissionPointRepository.findFirstBySubsidiaryIdeOrderByIdeAsc(subsidiary.getIde());

            senderNewDTO.setEmisionPoint(emissionPoint.get().getCodePoint());
            senderNewDTO.setSubsidiary(subsidiary.getCode());

            // Seteo el primer putno emision, este representa como si fuera el principal

            return senderNewDTO;

        } catch (DataAccessException e) {
            LOGGER.info("Error buscar emisor para editar", e);
            throw new NotDataAccessException("Ocurrio un error al buscar emisor para editar: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<String> findNameSenderByRuc(String ruc) {

        return senderRepository.findNameSenderByRuc(ruc);
    }

    @Override
    @Transactional(readOnly = true)
    public Long findIdByRuc(String ruc) {
        LOGGER.info("servicio: " + ruc);
        return senderRepository.findIdByRuc(ruc).orElse(-1L);
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
    @Transactional(readOnly = true)
    public SenderNewDTO findByIdCount(Long idCount) {

     String rucSender = countRepository.findRucById(idCount)
             .orElseThrow( () ->
                     new NotFoundException(String.format("Cuenta %s %s",
                             idCount, ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION)));

     // En caso que solo haya creado la cuenta, pero aun no haya registrado sus datos de emisor
     Sender sender = senderRepository.findByRuc(rucSender).orElseThrow(
             () ->
                     new NotFoundException(String.format("Cuenta %s no tiene un emisor registrado",
                             rucSender)));
        return senderMapper.mapperToDTOEdit(sender);
    }

    @Override
    @Transactional
    public SenderResponseDTO update(SenderNewDTO senderNewDTO, MultipartFile logo,
                                    MultipartFile certicado) {

        try {

            Sender senderCurrent = senderRepository.findByRuc(ConstanteUtil.TOKEN_USER)
                    .orElseThrow(() -> new NotFoundException(
                            String.format("Emisor %s %s", ConstanteUtil.TOKEN_USER,
                                    ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION)
                    ));

            AdminSender.update(senderCurrent, senderNewDTO);

            //Actualizar Encryptar password certificado
            senderCurrent.setPasswordCerticate(encryptionService.encrypt(senderNewDTO.getPasswordCerticate()));

            // Editar archivos
            if (logo != null) {

                // Obtener el logo actual
                String logoActual = senderCurrent.getLogo();

                boolean isDeleted = true;

                // Verificar si tiene un logo registrado, entonces lo eliminados antes de actualizar
                if (logoActual != null && !logoActual.isEmpty()) {
                    isDeleted = uploadFileService.deleteFile(PathDocuments.PATH_BASE.concat(senderCurrent.getRuc()).concat("/").concat(logoActual));
                }
                if (isDeleted) {
                    String nameFile = uploadFileService.saveFile(logo, PathDocuments.PATH_BASE.concat(senderCurrent.getRuc()), TypeFileEnum.IMG);
                    senderCurrent.setLogo(nameFile);
                }
            }

            if (certicado != null) {

                // Obtener el certificado actual
                String certicadoActual = senderCurrent.getNameCerticate();
                boolean isDeleted = true;

                if (certicadoActual != null && !certicadoActual.isEmpty()) {
                    isDeleted = uploadFileService.deleteFile(PathDocuments.PATH_BASE.concat(senderCurrent.getRuc()).concat("/").concat(certicadoActual));
                }

                if (isDeleted) {
                    String nameFileCerti = uploadFileService.saveFile(certicado, PathDocuments.PATH_BASE.concat(senderCurrent.getRuc()), TypeFileEnum.FILE);
                    senderCurrent.setNameCerticate(nameFileCerti);
                }


            }
            Sender senderUpdated = senderRepository.save(senderCurrent);
            return senderMapper.mapperToDTO(senderUpdated);

        } catch (DataAccessException e) {
            LOGGER.info("Error actualizar emisor", e);
            throw new NotDataAccessException("Ocurrio un error al  actualizar el emisor " + e.getMessage());
        }

    }

}
