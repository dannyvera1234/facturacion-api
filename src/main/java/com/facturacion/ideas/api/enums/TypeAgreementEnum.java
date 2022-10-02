package com.facturacion.ideas.api.enums;

public enum TypeAgreementEnum {

	MONTH, YEAR;

	public static TypeAgreementEnum getTypeAgreementEnum(String name) {

		if (name == null) {

			return TypeAgreementEnum.MONTH;
		}

		return name.toLowerCase().equals(TypeAgreementEnum.YEAR.name().toLowerCase()) ? TypeAgreementEnum.YEAR
				: TypeAgreementEnum.MONTH;
	}
}
