package com.facturacion.ideas.api.enums;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.List;

public class TestProvinceEnum {

    @Test
    void testCantidadProvincias() {

        List<ProvinceEnum> provinceEnumList = ProvinceEnum.getListProvinceEnum();
        assertEquals(24, provinceEnumList.size());

    }

    @Test
    void buscarProvincia() {

        String codigoProvincia = "23";
        ProvinceEnum provinceEnum = ProvinceEnum.getProvinceEnum(codigoProvincia);
        //assertEquals("SANTO DOMINGO DE LOS TSACHILAS", provinceEnum.getName());

        assertEquals("23", provinceEnum.getCode());
    }

    @Test
    void buscarProvinciaNula() {

        ProvinceEnum provinceEnum = ProvinceEnum.getProvinceEnum("26");
        assertNull(provinceEnum);
    }

    @Test
    void validarCodigosProvincias() {

        int totalProvincias = 0;
        for (int i = 1; i <= 24; i++) {

            String codigo = (i + "").length() == 1 ? ("0" + i) : i + "";

            System.out.println(codigo);
            ProvinceEnum provinceEnum = ProvinceEnum.getProvinceEnum(codigo);
            if (provinceEnum != null) totalProvincias += 1;
        }
        assertEquals(24, totalProvincias);
    }


}
