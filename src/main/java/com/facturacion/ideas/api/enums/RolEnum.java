package com.facturacion.ideas.api.enums;

public enum RolEnum {

	ADMIN, USSER;

	public static RolEnum getRolEnum(String rol) {

		if (rol == null) {

			return RolEnum.USSER;
		}
		return rol.equalsIgnoreCase("admin") ? RolEnum.ADMIN : RolEnum.USSER;
	}

}
