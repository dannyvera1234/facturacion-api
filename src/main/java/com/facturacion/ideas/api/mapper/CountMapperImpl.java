package com.facturacion.ideas.api.mapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.facturacion.ideas.api.dto.CountNewDTO;
import com.facturacion.ideas.api.dto.CountResponseDTO;
import com.facturacion.ideas.api.dto.DetailsAgreementDTO;
import com.facturacion.ideas.api.dto.LoginDTO;
import com.facturacion.ideas.api.entities.Count;
import com.facturacion.ideas.api.entities.Login;
import com.facturacion.ideas.api.enums.RolEnum;
import com.facturacion.ideas.api.util.FunctionUtil;

@Component
public class CountMapperImpl implements ICountMapper {

	@Autowired
	private IDetailsAgreementMapper detailsAgreementMapper;

	@Override
	public Count mapperToEntity(CountNewDTO countNewDTO) {

		Count count = new Count();
		count.setRuc(countNewDTO.getRuc());
		count.setPassword(countNewDTO.getPassword());
		count.setEstado(countNewDTO.isEstado());
		count.setRol(RolEnum.getRolEnum(countNewDTO.getRol()));

		return count;
	}

	@Override
	public CountResponseDTO mapperToDTO(Count count) {

		CountResponseDTO countResponseDTO = new CountResponseDTO();

		countResponseDTO.setIde(count.getIde());
		countResponseDTO.setRuc(count.getRuc());
		
		List<DetailsAgreementDTO> detailsAgreementDTOs = detailsAgreementMapper
				.mapperToDTO(count.getDetailsAggrement());

		if (detailsAgreementDTOs.size() > 0) {

			// Ordenar plan contrarado para obtener el ultimo plan contratado
			Collections.sort(detailsAgreementDTOs);

			// Obtengo el ultimo plan contratado

			DetailsAgreementDTO detailsAgreementDTO = detailsAgreementDTOs.get(0);
			countResponseDTO.setAggrement(detailsAgreementDTO.getAgreement());
			countResponseDTO.setAmount(detailsAgreementDTO.getAmount());
		}

		countResponseDTO.setRol(count.getRol().name());
		countResponseDTO.setFechaRegistro(FunctionUtil.convertDateToString(count.getFechaRegistro()));
		countResponseDTO.setEstado(count.isEstado());
		return countResponseDTO;
	}

	@Override
	public List<CountResponseDTO> mapperToDTO(List<Count> counts) {

		List<CountResponseDTO> countResponseDTOs = new ArrayList<>();

		if (counts.size() > 0) {

			countResponseDTOs = counts.stream().map(item -> mapperToDTO(item)).collect(Collectors.toList());

		}

		return countResponseDTOs;
	}

	@Override
	public Login mapperToEntity(LoginDTO loginDTO) {

		Login login = new Login();
		return login;
	}

	@Override
	public LoginDTO mapperToEntity(Login login) {

		LoginDTO loginDTO = new LoginDTO();

		loginDTO.setIde(login.getIde());

		loginDTO.setDateLogIn(FunctionUtil.convertDateToString(login.getDateLogIn()));

		loginDTO.setDateLogOut(FunctionUtil.convertDateToString(login.getDateLogOut()));

		return loginDTO;
	}

	@Override
	public List<LoginDTO> mapperToEntity(List<Login> logins) {

		List<LoginDTO> loginDTOs = new ArrayList<>();

		if (logins.size() > 0) {

			loginDTOs = logins.stream().map(item -> mapperToEntity(item)).collect(Collectors.toList());
		}

		return loginDTOs;
	}

}
