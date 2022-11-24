package com.facturacion.ideas.api.enums;

public enum TypeFileEnum {

    IMG("IMG", "01"),
    FILE("FILE", "02");

    private String type;
    private String code;

    TypeFileEnum(String type, String code) {
        this.type = type;
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public String getCode() {
        return code;
    }
}
