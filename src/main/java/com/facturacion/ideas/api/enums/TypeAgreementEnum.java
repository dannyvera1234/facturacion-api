package com.facturacion.ideas.api.enums;

import java.util.Arrays;
import java.util.List;

public enum TypeAgreementEnum {

    MONTH(1), YEAR(1), YEAR_PLUS(1);

    private int number;

    private TypeAgreementEnum(int number) {

        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public static TypeAgreementEnum getTypeAgreementEnum(String name) {


        return name == null ? null : getListTypeAgreementEnum().stream().filter(item -> item.name().equals(name)).findFirst().orElse(null);
    }

    public static List<TypeAgreementEnum> getListTypeAgreementEnum() {

        return Arrays.asList(TypeAgreementEnum.MONTH, TypeAgreementEnum.YEAR, TypeAgreementEnum.YEAR_PLUS);
    }
}
