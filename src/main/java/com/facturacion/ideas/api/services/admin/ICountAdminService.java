package com.facturacion.ideas.api.services.admin;

import com.facturacion.ideas.api.dto.CountNewDTO;
import com.facturacion.ideas.api.dto.CountResponseDTO;
import com.facturacion.ideas.api.dto.SenderResponseDTO;
import com.facturacion.ideas.api.dto.SubsidiaryAndEmissionPointDTO;

import java.util.List;

public interface ICountAdminService {

    CountResponseDTO saveCount(CountNewDTO countNewDTO);

    List<CountResponseDTO> fetchByWithAgreement();

    CountResponseDTO updateCountStatus(Long ide);

    void deleteCountById(final Long id);

}
