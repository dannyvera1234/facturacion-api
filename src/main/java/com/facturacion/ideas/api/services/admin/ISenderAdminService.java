package com.facturacion.ideas.api.services.admin;

import com.facturacion.ideas.api.dto.SenderResponseDTO;

import java.util.List;

public interface ISenderAdminService {

    List<SenderResponseDTO> findAll();
}
