package com.facturacion.ideas.api.enums;

public enum StatusDocumentsEnum {
    PROCESAMIENTO("En procesamiento", "PPR"),
    AUTORIZADO("Autorizado", "AUT"),
    NO_AUTORIZADO("No autorizado", "NAT"),;

    private  String name;
    private  String siglas;

    StatusDocumentsEnum(String name, String siglas) {
        this.name = name;
        this.siglas = siglas;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSiglas() {
        return siglas;
    }

    public void setSiglas(String siglas) {
        this.siglas = siglas;
    }
}
