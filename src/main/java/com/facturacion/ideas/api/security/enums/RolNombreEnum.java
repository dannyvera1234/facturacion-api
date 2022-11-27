package com.facturacion.ideas.api.security.enums;

import java.util.List;
import java.util.Set;

public enum RolNombreEnum {

    ROLE_ADMIN, ROLE_USER, ROLE_EMP;


    public static RolNombreEnum getRolNombreEnum(String rol) {

        if (rol == null) return null;
        return getSetRolNombreEnum()
                .stream()
                .filter(rolItem -> rolItem.name().equalsIgnoreCase(rol))
                .findFirst().orElse(null);
    }


    public static Set<RolNombreEnum> getSetRolNombreEnum() {
        return Set.of(ROLE_ADMIN, ROLE_USER, ROLE_EMP);
    }
}
