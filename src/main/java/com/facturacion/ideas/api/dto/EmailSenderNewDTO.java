package com.facturacion.ideas.api.dto;

import java.io.Serializable;
public class EmailSenderNewDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String email;

    private boolean principal;

    public EmailSenderNewDTO() {
    }

    public EmailSenderNewDTO(String email, boolean principal) {
        this.email = email;
        this.principal = principal;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public boolean isPrincipal() {
        return principal;
    }

    public void setPrincipal(boolean principal) {
        this.principal = principal;
    }

    @Override
    public String toString() {
        return "EmailSenderNewDTO{" +
                "email='" + email + '\'' +
                ", isPrincipal=" + principal +
                '}';
    }
}

