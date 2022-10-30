package com.facturacion.ideas.api.dto;

import java.io.Serializable;
public class EmailSenderNewDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String email;

    private boolean isPrincipal;

    public EmailSenderNewDTO() {
    }

    public EmailSenderNewDTO(String email, boolean isPrincipal) {
        this.email = email;
        this.isPrincipal = isPrincipal;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isPrincipal() {
        return isPrincipal;
    }

    public void setPrincipal(boolean principal) {
        isPrincipal = principal;
    }

    @Override
    public String toString() {
        return "EmailSenderNewDTO{" +
                "email='" + email + '\'' +
                ", isPrincipal=" + isPrincipal +
                '}';
    }
}

