package com.facturacion.ideas.api.enums;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ProvinceEnum {

	AZUAY("01", "07", "AZUAY"), BOLIVAR("02", "03", "BOLÍVAR"), CANNAR("03", "07", "CAÑAR"),
	CARCHI("04", "06", "CARCHI"), COTOPAXI("05", "03", "COTOPAXI"), CHIMBORAZO("06", "03", "CHIMBORAZO"),
	EL_ORO("07", "07", "EL ORO"), ESMERALDAS("08", "06", "ESMERALDAS"), GUAYAS("09", "04", "GUAYAS"),
	IMBABURA("10", "06", "IMBABURA"), LOJA("11", "07", "LOJA"), LOS_RIOS("12", "05", "LOS RÍOS"),
	MANABI("13", "05", "MANABÍ"), MORONA_SANTIAGO("14", "07", "MORONA SANTIAGO"), NAPO("15", "06", "NAPO"),
	PASTAZA("16", "03", "PASTAZA"), PICHINCHA("17", "02", "PICHINCHA"), TUNGURAHUA("18", "03", "TUNGURAHUA"),
	ZAMORA_CHINCHIPE("19", "07", "ZAMORA CHINCHIPE"), GALAPAGOS("20", "05", "GALÁPAGOS"),
	ORELLANA("21", "06", "ORELLANA"), SUCUMBIOS("22", "06", "SUCUMBIOS"),
	SANTO_DOMINGO("23", "02", "SANTO DOMINGO DE LOS TSACHILAS"), SANTA_ELENA("24", "04", "SANTA ELENA");

	private String code;
	private String codeTlf;
	private String name;

	private ProvinceEnum(String code, String codeTlf, String name) {

		this.code = code;
		this.codeTlf = codeTlf;
		this.name = name;
	}

	@JsonValue
	public String getCode() {
		return code;
	}

	public String getCodeTlf() {
		return codeTlf;
	}

	public String getName() {
		return name;
	}

	public static ProvinceEnum getProvinceEnum(String code) {

		ProvinceEnum province = null;

		if (code != null) {

			province = getListProvinceEnum().stream().filter(item -> item.getCode().equals(code)).findFirst()
					.orElse(null);

		}

		return province;

	}

	public static List<ProvinceEnum> getListProvinceEnum() {

		return Arrays.asList(ProvinceEnum.AZUAY, ProvinceEnum.BOLIVAR, ProvinceEnum.CANNAR, ProvinceEnum.CARCHI,
				ProvinceEnum.COTOPAXI, ProvinceEnum.CHIMBORAZO, ProvinceEnum.EL_ORO, ProvinceEnum.ESMERALDAS,
				ProvinceEnum.GUAYAS, ProvinceEnum.IMBABURA, ProvinceEnum.LOJA, ProvinceEnum.LOS_RIOS,
				ProvinceEnum.MANABI, ProvinceEnum.MORONA_SANTIAGO, ProvinceEnum.NAPO, ProvinceEnum.PASTAZA,
				ProvinceEnum.PICHINCHA, ProvinceEnum.TUNGURAHUA, ProvinceEnum.ZAMORA_CHINCHIPE, ProvinceEnum.GALAPAGOS,
				ProvinceEnum.ORELLANA, ProvinceEnum.SUCUMBIOS, ProvinceEnum.SANTO_DOMINGO, ProvinceEnum.SANTA_ELENA);
	}

}
