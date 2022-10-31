package com.facturacion.ideas.api.services;

import java.util.List;
import com.facturacion.ideas.api.dto.AgreementDTO;
import com.facturacion.ideas.api.dto.DetailsAgreementDTO;
import com.facturacion.ideas.api.services.admin.IAgreementAdminService;

public interface IAgreementService extends IAgreementAdminService {

	AgreementDTO findById(Long  codigo);

	AgreementDTO findByType(String type);
}
