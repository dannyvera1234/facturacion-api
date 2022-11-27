package com.facturacion.ideas.api.security.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class LoginUserDTO implements Serializable {

    private static final long serialVersionUID = 1L;


    @NotBlank(message = "EL ruc no puede estar vacío")
    @Size(min = 13, max = 13, message = "El ruc debe tener 13 dígitos")
    private String ruc;

    @NotBlank(message = "La contraseña no puede estar vacío")
    private String password;

    public LoginUserDTO(String ruc, String password) {
        this.ruc = ruc;
        this.password = password;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String toString() {
        return "LoginUserDTO{" +
                "ruc='" + ruc + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
