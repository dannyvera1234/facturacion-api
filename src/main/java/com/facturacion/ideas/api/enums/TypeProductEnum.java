package com.facturacion.ideas.api.enums;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TypeProductEnum {

	BIEN("1"), SERVICIO("2");

	private String code;

	private TypeProductEnum(String code) {

		this.code = code;
	}

	@JsonValue
	public String getCode() {
		return code;
	}

	public static TypeProductEnum getTypeProductEnum(String code) {

		TypeProductEnum typeProductEnum = null;

		if (code != null) {

			typeProductEnum = listTypeProductEnum().stream().filter(item -> item.getCode().equals(code)).findFirst()
					.orElse(null);

		}

		return typeProductEnum;
	}

	public static List<TypeProductEnum> listTypeProductEnum() {

		return Arrays.asList(TypeProductEnum.BIEN, TypeProductEnum.SERVICIO);

	}

}
