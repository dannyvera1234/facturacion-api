package com.facturacion.ideas.api.enums;

public enum RolEnum {

	ADMIN, USSER;

	public static RolEnum getRolEnum(String rol) {

		if (rol == null) {

			return RolEnum.USSER;
		}
		return rol.toLowerCase().equals("admin") ? RolEnum.ADMIN : RolEnum.USSER;
	}

}
