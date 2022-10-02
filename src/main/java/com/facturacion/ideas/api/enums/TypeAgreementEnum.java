package com.facturacion.ideas.api.enums;

public enum TypeAgreementEnum {

	MONTH(1), YEAR(1);

	private int number;
	private TypeAgreementEnum(int number) {
		
		this.number = number;
	}
	
	public int getNumber() {
		return number;
	}
	
	public static TypeAgreementEnum getTypeAgreementEnum(String name) {

		if (name == null) {

			return TypeAgreementEnum.MONTH;
		}

		return name.toLowerCase().equals(TypeAgreementEnum.YEAR.name().toLowerCase()) ? TypeAgreementEnum.YEAR
				: TypeAgreementEnum.MONTH;
	}
}
