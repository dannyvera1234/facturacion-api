package com.facturacion.ideas.api.security.service;

import com.facturacion.ideas.api.entities.Count;

import java.util.Optional;

public interface IUserService {
    Optional<Count> getCountByRuc(String ruc);
    boolean existsByRuc(String  ruc);
}
