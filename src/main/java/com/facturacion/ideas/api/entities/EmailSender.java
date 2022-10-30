package com.facturacion.ideas.api.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="email_emisor")
public class EmailSender implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="EMA_COD")
    private  Long ide;

    @Column(name="EMA_DIR")
    private  String email;

    @Column(name="EMA_FEC")
    private Date dateCreate;

    @Column(name="EMA_PRI")
    private  boolean principal;

    public EmailSender() {
    }

    public EmailSender(String email, Date dateCreate, boolean principal) {
        this.email = email;
        this.dateCreate = dateCreate;
        this.principal = principal;
    }

    public Long getIde() {
        return ide;
    }

    public void setIde(Long ide) {
        this.ide = ide;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public boolean isPrincipal() {
        return principal;
    }

    public void setPrincipal(boolean principal) {
        this.principal = principal;
    }

    @Override
    public String toString() {
        return "EmailSender{" +
                "ide=" + ide +
                ", email='" + email + '\'' +
                ", dateCreate=" + dateCreate +
                ", isPrincipal=" + principal +
                '}';
    }
}

