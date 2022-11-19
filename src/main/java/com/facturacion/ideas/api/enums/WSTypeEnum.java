package com.facturacion.ideas.api.enums;

public enum WSTypeEnum {

    WS_TEST_RECEPTION("RECEPCION", "https://celcer.sri.gob.ec/comprobantes-electronicos-ws/RecepcionComprobantesOffline?wsdl", TypeEnvironmentEnum.PRUEBAS),
    WS_TEST_AUTHORIZATION("AUTORIZACION", "https://celcer.sri.gob.ec/comprobantes-electronicos-ws/AutorizacionComprobantesOffline?wsdl", TypeEnvironmentEnum.PRUEBAS),
    WS_PROD_RECEPTION("RECEPCION", "https://cel.sri.gob.ec/comprobantes-electronicos-ws/RecepcionComprobantesOffline?wsdl", TypeEnvironmentEnum.PRODUCCION),
    WS_PROD_AUTHORIZATION("AUTORIZACION", "https://cel.sri.gob.ec/comprobantes-electronicos-ws/AutorizacionComprobantesOffline?wsdl", TypeEnvironmentEnum.PRODUCCION);


    private final String nameService;
    private final String wsdl;
    private final TypeEnvironmentEnum environmentEnum;

    WSTypeEnum(String nameService, String wsdl, TypeEnvironmentEnum environmentEnum) {

        this.nameService = nameService;
        this.wsdl = wsdl;
        this.environmentEnum = environmentEnum;
    }

    public String getWsdl() {
        return wsdl;
    }

    public String getNameService() {
        return nameService;
    }

    public TypeEnvironmentEnum getEnvironmentEnum() {
        return environmentEnum;
    }

}
