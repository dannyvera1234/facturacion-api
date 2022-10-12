package com.facturacion.ideas.api.enums;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TypeDocumentEnum {

	FACTURA("01", "FACTURA"),
	LIQUIDACIÓN_COMPRA_BIENES_PRESTACIÓN_SERVICIOS("03", "LIQUIDACIÓN DE COMPRA DE BIENES Y PRESTACIÓN DE SERVICIOS"),
	NOTA_CREDITO("04", "NOTA DE CREDITO"), NOTA_DEBITO("05", "NOTA DE DEBITO"), GUIA_REMISION("06", "GUÍA DE REMISIÓN"),
	COMPROBANTE_RETENCION("07", "COMPROBANTE DE RETENCIÓN");

	private String code;
	private String description;

	private TypeDocumentEnum() {
	}

	private TypeDocumentEnum(String code, String description) {
		this.code = code;
		this.description = description;
	}

	@JsonValue
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public static TypeDocumentEnum getTypeDocumentEnum(String codigo) {

		TypeDocumentEnum tipoDocumentoEnum = TypeDocumentEnum.FACTURA;

		if (codigo != null) {

			tipoDocumentoEnum = getListTypeDocumentEnum().stream().filter(item -> item.getCode().equals(codigo))
					.findAny().orElse(TypeDocumentEnum.FACTURA);

		}

		return tipoDocumentoEnum;
	}

	public static List<TypeDocumentEnum> getListTypeDocumentEnum() {

		return Arrays.asList(TypeDocumentEnum.FACTURA, TypeDocumentEnum.LIQUIDACIÓN_COMPRA_BIENES_PRESTACIÓN_SERVICIOS,
				TypeDocumentEnum.NOTA_CREDITO, TypeDocumentEnum.NOTA_DEBITO, TypeDocumentEnum.GUIA_REMISION,
				TypeDocumentEnum.COMPROBANTE_RETENCION);
	}
}
