package com.facturacion.ideas.api.enums;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TypeCustomerEnum {

	C("CLIENTE", "C"), R("SUJETO RETENIDO", "R"), D("DESTINATARIO", "D");

	private String descripcion;

	private String code;

	TypeCustomerEnum(String descripcion, String code) {
		this.code = code;
		this.descripcion = descripcion;
	}

	@JsonValue
	public String getCode() {
		return this.code;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public String toString() {
		return this.descripcion;
	}

	public static TypeCustomerEnum getTypeCustomerEnum(String codigo) {

		if (codigo != null) {

			return getListTypeCustomerEnum().stream().filter(item -> item.getCode().equals(codigo)).findFirst()
					.orElse(null);
		}

		return null;

	}

	public static List<TypeCustomerEnum> getListTypeCustomerEnum() {

		return Arrays.asList(TypeCustomerEnum.C, TypeCustomerEnum.D, TypeCustomerEnum.R);
	}
}
