package com.facturacion.ideas.api.enums;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonValue;
public enum TypeEmissionEnum {

	
	NORMAL("1");

	private String code;

	TypeEmissionEnum(String code) {

		this.code = code;
	}

	@JsonValue
	public String getCode() {
		return code;
	}

	public static TypeEmissionEnum getTypeEmissionEnum(String code) {

		TypeEmissionEnum typeEmission = null;

		if (code != null) {
			typeEmission = getListTypeEmissionEnum()
												.stream()
												.filter(item -> item.getCode().equals(code))
												.findFirst().orElse(null);
					
		}

		return typeEmission;
	}
	
	public static List<TypeEmissionEnum> getListTypeEmissionEnum(){
		
		
		return Arrays.asList(TypeEmissionEnum.NORMAL);
	}
	
}
