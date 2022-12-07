package com.facturacion.ideas.api.admin;

import java.util.Calendar;
import java.util.Date;

import com.facturacion.ideas.api.dto.EmissionPointNewDTO;
import com.facturacion.ideas.api.entities.EmissionPoint;
import com.facturacion.ideas.api.util.ConstanteUtil;
import com.facturacion.ideas.api.util.FunctionUtil;

public class AdminEmissionPoint {

    /**
     * Crear un nuevo {@link EmissionPoint}}
     *
     * @param numberNext : Valor entero que corresponde al numero secuencial para el
     *                   punto emision. </br>
     *                   Si {@link numberNext} es null, indica que es el primer
     *                   {@link EmissionPoint} del establecimiento
     * @param rucSender  : EL ruc de Sender que es dueña del establecimiento, se
     *                   utilizara para generar la clave del EmissionPoint
     * @return
     */
    public static EmissionPoint create(Integer numberNext, String rucSender) {

        EmissionPoint emissionPoint = new EmissionPoint();

        numberNext = getNumberNextEmissionPoint(numberNext);

        emissionPoint.setCodePoint(getCodEmissionPoint(numberNext));
        emissionPoint.setDateRegister(new Date());
        emissionPoint.setKeyPoint(rucSender + "_" + emissionPoint.getCodePoint());
        emissionPoint.setStatus(true);

        return emissionPoint;
    }

    /**
     * Funcion que genera el codigo para un nuevo punto de emision .Si
     * numEmissionPoint es null ubicara el valor por defecto de
     * {@link com.facturacion.ideas.api.util.ConstanteUtil#COD_DEFAULT_EMISSION_POINT}
     *
     * @param numEmissionPoint : numero de punto emision a generar
     * @return :
     */
    private static String getCodEmissionPoint(Integer numEmissionPoint) {

        if (numEmissionPoint != null) {

            String codGenerar = null;

            if (numEmissionPoint < 10)
                codGenerar = "00" + numEmissionPoint;
            else if (numEmissionPoint >= 10 && numEmissionPoint < 100)
                codGenerar = "0" + numEmissionPoint;

            else
                codGenerar = "" + numEmissionPoint;

            return codGenerar;
        }
        return ConstanteUtil.COD_DEFAULT_EMISSION_POINT;

    }

    public static EmissionPoint create(String codePoint) {

        EmissionPoint emissionPoint = new EmissionPoint();
        emissionPoint.setCodePoint(codePoint);
        emissionPoint.setDateRegister(new Date());;
       // emissionPoint.setStatus(false);
        return emissionPoint;
    }


    private static Integer getNumberNextEmissionPoint(Integer numberNext) {

        return numberNext == null ? 1 : (numberNext + 1);
    }

}
