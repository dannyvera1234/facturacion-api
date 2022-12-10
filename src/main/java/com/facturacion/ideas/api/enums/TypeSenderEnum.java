package com.facturacion.ideas.api.enums;

public enum TypeSenderEnum {
	NATURAL,
	JURIDICA;
	public static  TypeSenderEnum findByCode(String code){
		if (code.equalsIgnoreCase(TypeSenderEnum.JURIDICA.name())){
			return  TypeSenderEnum.JURIDICA;
		}

		return  TypeSenderEnum.NATURAL;
	}
}
