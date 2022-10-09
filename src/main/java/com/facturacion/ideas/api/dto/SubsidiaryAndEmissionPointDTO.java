package com.facturacion.ideas.api.dto;

import java.io.Serializable;
import java.util.List;

public class SubsidiaryAndEmissionPointDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String code;

	private boolean status;

	private List<EmissionPointResponseDTO> emissionPointResponseDTO;

	public SubsidiaryAndEmissionPointDTO() {
	}

	public SubsidiaryAndEmissionPointDTO(String code, boolean status,
			List<EmissionPointResponseDTO> emissionPointResponseDTO) {
		super();
		this.code = code;
		this.status = status;
		this.emissionPointResponseDTO = emissionPointResponseDTO;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public List<EmissionPointResponseDTO> getEmissionPointResponseDTO() {
		return emissionPointResponseDTO;
	}

	public void setEmissionPointResponseDTO(List<EmissionPointResponseDTO> emissionPointResponseDTO) {
		this.emissionPointResponseDTO = emissionPointResponseDTO;
	}

	public void addEmissionPointResponseDTO(EmissionPointResponseDTO emissionPointResponseDTO) {
		this.emissionPointResponseDTO.add(emissionPointResponseDTO);
	}

	@Override
	public String toString() {
		return "SubsidiaryAndEmissionPointDTO [code=" + code + ", status=" + status + ", emissionPointResponseDTO="
				+ emissionPointResponseDTO.size() + "]";
	}

}
