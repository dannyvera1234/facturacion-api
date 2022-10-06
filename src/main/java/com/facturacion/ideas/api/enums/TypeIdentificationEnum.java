package com.facturacion.ideas.api.enums;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TypeIdentificationEnum {

	CONSUMIDOR_FINAL("07"), RUC("04"), CEDULA("05"), PASAPORTE("06"), IDENTIFICACION_EXTERIOR("08"), PLACA("09");

	private String code;

	TypeIdentificationEnum(String code) {
		this.code = code;
	}

	@JsonValue
	public String getCode() {
		return this.code;
	}

	public static TypeIdentificationEnum getTipoCompradorEnum(String codigo) {

		TypeIdentificationEnum tipoComprador = TypeIdentificationEnum.CONSUMIDOR_FINAL;

		if (codigo != null) {

			tipoComprador = getListTypeIdentificationEnum().stream().filter(item -> item.getCode().equals(codigo))
					.findFirst().orElse(TypeIdentificationEnum.CONSUMIDOR_FINAL);

		}
		return tipoComprador;

	}

	public static List<TypeIdentificationEnum> getListTypeIdentificationEnum() {

		return Arrays.asList(TypeIdentificationEnum.CONSUMIDOR_FINAL, TypeIdentificationEnum.CEDULA,
				TypeIdentificationEnum.RUC, TypeIdentificationEnum.PASAPORTE,
				TypeIdentificationEnum.IDENTIFICACION_EXTERIOR, TypeIdentificationEnum.PLACA);
	}

}
