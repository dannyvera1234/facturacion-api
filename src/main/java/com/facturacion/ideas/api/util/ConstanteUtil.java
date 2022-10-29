package com.facturacion.ideas.api.util;

public class ConstanteUtil {

	
	public static final String COD_DEFAULT_SUBSIDIARY = "001";
	
	public static final String COD_DEFAULT_EMISSION_POINT = "001";
	
	public static final String MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION = " no esta registrado en la Base de Datos";
	
	public static final String MESSAJE_DUPLICATED_RESOURCE_DEFAULT_EXCEPTION = " ya esta registrado en la Base de Datos";
	
	public static final String CROOS_ORIGIN = "http://localhost:4200";
	
	public static final String DATE_FORMAT_DEFAULT = "yyyy-MM-dd";
	
	// Formato para generar la clave de acceso de los comprobantes
	public static final String DATE_FORMAT_KEY_ACCESS = "ddMMyyyy";
	
	// Codigo numerico para la generacion del clave de acceso, puede ser cualquier valor de 8 digitos
	public static final String CODE_NUMERIC_KEY_ACCESS = "22040612";
	
	// Texto por defecto para las personas que sean tipo regimen rimpe, esto texto no se persiste en la BD
	public static final String TEXT_DEFAULT_REGIMEN_RIMPE = "CONTRIBUYENTE RÉGIMEN RIMPE";
	
	// Texto para expresar la razon social
	public static final String TEXT_DEFAULT_CONSUMIDOR_FINAL = "CONSUMIDOR FINAL";
	
	// Texto numero de indentificación para el consumidor final, 13 digitos
	public static final String TEXT_DEFAULT_CODE_CONSUMIDOR_FINAL = "9999999999999";
	
	// Tipo de moneda
	public static final String TEXT_DEFAULT_MODEDA = "DOLAR";

	// Iva actual en porcentaje
	public static final Double IVA_ACTUAL_PORCENTAJE = 12.0;

	// Iva actual en decimal
	public static final Double IVA_ACTUAL_DECIMAL = 1.12;

	// valor agegado expresado en Dolar
	public static final Double VALOR_IVA_IRBPNR = 0.02;
	
	
	
}
