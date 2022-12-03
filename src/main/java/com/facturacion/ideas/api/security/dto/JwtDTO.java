package com.facturacion.ideas.api.security.dto;

import java.io.Serializable;


public class JwtDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String token;

    public JwtDTO() {
    }

    public JwtDTO(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
