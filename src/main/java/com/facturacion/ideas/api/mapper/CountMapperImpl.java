package com.facturacion.ideas.api.mapper;

import java.util.*;
import java.util.stream.Collectors;

import com.facturacion.ideas.api.security.dto.RolNewDTO;
import com.facturacion.ideas.api.security.entity.Rol;
import com.facturacion.ideas.api.security.enums.RolNombreEnum;
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


       /* String rol = countNewDTO.getRoles();

        Set<RolNewDTO> roles = new HashSet<>();

        // Todos los usuarios tendran un rol almenos de empleado
        roles.add(new RolNewDTO(RolNombreEnum.ROLE_EMP.name()));


        // Roles para el admin, tendra los 3 roles
        if (rol.equalsIgnoreCase(RolNombreEnum.ROLE_ADMIN.name())) {
            roles.add(new RolNewDTO(RolNombreEnum.ROLE_ADMIN.name()));
            roles.add(new RolNewDTO(RolNombreEnum.ROLE_USER.name()));
        }


        // Roles para el user, tendra  solo 2 roles
        if (rol.equalsIgnoreCase(RolNombreEnum.ROLE_USER.name())) {
            roles.add(new RolNewDTO(RolNombreEnum.ROLE_USER.name()));
        }

        count.setRoles(mapperToEntity(roles));
        
        */

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

        /*countResponseDTO.setRol(
                count.getRoles().stream().map(item -> item.getRolNombreEnum().name())
                                .reduce("", (otro, elemento) -> elemento+","));
        */
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

    @Override
    public Rol mapperToEntity(RolNewDTO rolNewDTO) {

        RolNombreEnum rolNombreEnum = RolNombreEnum.getRolNombreEnum(rolNewDTO.getRolNombreEnum());
        Rol rol = null;
        if (rolNombreEnum != null) {
            rol = new Rol();
            rol.setRolNombreEnum(rolNombreEnum);
        }

        return rol;
    }

    @Override
    public Set<Rol> mapperToEntity(Set<RolNewDTO> rolNewDTO) {

        Set<Rol> roles = new HashSet<>(1);
        rolNewDTO.forEach(item -> {

            Rol rol = mapperToEntity(item);
            if (rol != null) roles.add(rol);

        });
        return roles;
    }

}
