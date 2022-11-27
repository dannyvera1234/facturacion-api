package com.facturacion.ideas.api.security.repository;

import com.facturacion.ideas.api.security.entity.Rol;
import com.facturacion.ideas.api.security.enums.RolNombreEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRolRepository extends JpaRepository<Rol, Long> {


    Optional<Rol>  findByRolNombreEnum(RolNombreEnum rolNombreEnum);
}
