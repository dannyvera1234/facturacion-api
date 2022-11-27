package com.facturacion.ideas.api.mapper;

import java.util.List;
import java.util.Set;

import com.facturacion.ideas.api.dto.CountNewDTO;
import com.facturacion.ideas.api.dto.CountResponseDTO;
import com.facturacion.ideas.api.dto.LoginDTO;
import com.facturacion.ideas.api.entities.Count;
import com.facturacion.ideas.api.entities.Login;
import com.facturacion.ideas.api.security.dto.RolNewDTO;
import com.facturacion.ideas.api.security.entity.Rol;

public interface ICountMapper {

    Count mapperToEntity(CountNewDTO countNewDTO);

    CountResponseDTO mapperToDTO(Count count);

    List<CountResponseDTO> mapperToDTO(List<Count> counts);

    Login mapperToEntity(LoginDTO loginDTO);

    LoginDTO mapperToEntity(Login login);

    List<LoginDTO> mapperToEntity(List<Login> logins);

    Rol mapperToEntity(RolNewDTO rolNewDTO);

    Set<Rol> mapperToEntity(Set<RolNewDTO> rolNewDTO);
}
