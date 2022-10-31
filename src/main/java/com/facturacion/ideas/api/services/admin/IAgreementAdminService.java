package com.facturacion.ideas.api.services.admin;

import com.facturacion.ideas.api.dto.AgreementDTO;

import java.util.List;

public interface IAgreementAdminService {

    AgreementDTO save(AgreementDTO agreementDTO);

    AgreementDTO update(AgreementDTO agreementDTO, Long code);

    void deleteById(Long codigo);

    List<AgreementDTO> listAll();

}
