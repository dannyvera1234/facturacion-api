package com.facturacion.ideas.api.security.entity;

import com.facturacion.ideas.api.security.enums.RolNombreEnum;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="roles")
public class Rol  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ROL_COD")
    private Long ide;

    @Enumerated(EnumType.STRING)
    @Column(name="ROL_NOM")
    private RolNombreEnum rolNombreEnum;

    public Rol() {
    }

    public Rol(RolNombreEnum rolNombreEnum) {
        this.rolNombreEnum = rolNombreEnum;
    }


    public Long getIde() {
        return ide;
    }

    public void setIde(Long ide) {
        this.ide = ide;
    }

    public RolNombreEnum getRolNombreEnum() {
        return rolNombreEnum;
    }

    public void setRolNombreEnum(RolNombreEnum rolNombreEnum) {
        this.rolNombreEnum = rolNombreEnum;
    }

    @Override
    public String toString() {
        return "Rol{" +
                "ide=" + ide +
                ", rolNombreEnum=" + rolNombreEnum +
                '}';
    }
}
