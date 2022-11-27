package com.facturacion.ideas.api.security.dto;
import java.io.Serializable;

public class RolNewDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String rolNombreEnum;

    public RolNewDTO(){}

    public RolNewDTO(String rolNombreEnum) {
        this.rolNombreEnum = rolNombreEnum;
    }

    public String getRolNombreEnum() {
        return rolNombreEnum;
    }

    public void setRolNombreEnum(String rolNombreEnum) {
        this.rolNombreEnum = rolNombreEnum;
    }

}
