package com.facturacion.ideas.api.enums;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TypeTaxEnum {

	RENTA("1", "Impuesto a la Renta"), IVA("2", "I.V.A."), ICE("3", "I.C.E."), IRBPNR("5", "IRBPNR"),
	ISD("6", "Impuesta a la Salida de Divisas");

	private String description;
	private String code;

	private TypeTaxEnum(String code, String description) {
		this.description = description;
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	@JsonValue
	public String getCode() {
		return code;
	}

	public static TypeTaxEnum getTypeTax(String code) {

		if (code != null)
			return getListTypeTax().stream().filter(item -> item.getCode().equals(code)).findFirst().orElse(null);

		return null;
	}

	public static List<TypeTaxEnum> getListTypeTax() {

		return Arrays.asList(TypeTaxEnum.RENTA, TypeTaxEnum.IVA, TypeTaxEnum.ICE, TypeTaxEnum.IRBPNR, TypeTaxEnum.ISD);
	}

}
