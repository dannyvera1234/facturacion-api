package com.facturacion.ideas.api.security.service;

import com.facturacion.ideas.api.security.entity.Rol;

import java.util.List;
import java.util.Optional;

public interface IRolService {
    Optional<Rol>  findByRolNombreEnum(String rolNombreEnum);

    List<Rol>  findALl();
}
