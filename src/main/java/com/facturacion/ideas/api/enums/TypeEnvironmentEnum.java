package com.facturacion.ideas.api.enums;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TypeEnvironmentEnum {

	PRODUCCION("2"), PRUEBAS("1");

	private String code;

	private TypeEnvironmentEnum(String code) {

		this.code = code;
	}


	@JsonValue
	public String getCode() {
		return code;
	}

	public static TypeEnvironmentEnum getTypeEnvironmentEnum(String code) {

		TypeEnvironmentEnum tipoAmbiente = null;

		if (code != null) {

			tipoAmbiente = getListTypeEnvironmentEnum().stream().filter(item -> item.getCode().equals(code)).findFirst()
					.orElse(null);

		}

		return tipoAmbiente;

	}

	public static List<TypeEnvironmentEnum> getListTypeEnvironmentEnum() {

		return Arrays.asList(TypeEnvironmentEnum.PRUEBAS, TypeEnvironmentEnum.PRODUCCION);

	}
}
