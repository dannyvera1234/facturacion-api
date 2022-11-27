package com.facturacion.ideas.api.security.service;

import com.facturacion.ideas.api.security.entity.Rol;
import com.facturacion.ideas.api.security.enums.RolNombreEnum;
import com.facturacion.ideas.api.security.repository.IRolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RolServiceImpl implements IRolService {

    @Autowired
    private IRolRepository rolRepository;

    @Transactional(readOnly = true)
    @Override
    public Optional<Rol> findByRolNombreEnum(String rolNombreEnum) {

        return rolRepository.findByRolNombreEnum(RolNombreEnum.getRolNombreEnum(rolNombreEnum));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Rol> findALl() {

        return rolRepository.findAll();
    }
}
