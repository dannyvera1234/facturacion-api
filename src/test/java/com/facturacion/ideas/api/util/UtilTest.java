package com.facturacion.ideas.api.util;

import com.facturacion.ideas.api.services.IUploadFileService;
import com.facturacion.ideas.api.services.UploadFileServiceImpl;
import org.junit.jupiter.api.Test;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;

public class UtilTest {

    // 2022-11-23T17:38:05-05:00

    @Test
    void testDateXmlConvert() {


        try {

            String fechaXml = "2022-11-23T17:38:05-05:00";

            XMLGregorianCalendar fecha = DatatypeFactory.newInstance().newXMLGregorianCalendar(fechaXml);

            Calendar calendar = Calendar.getInstance();

            calendar.setTime(fecha.toGregorianCalendar().getTime());

            String fechaString = calendar.get(Calendar.YEAR) + "-" +
                    ((calendar.get(Calendar.MONTH)) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);

            assertEquals("2022-11-23", fechaString);

        } catch (DatatypeConfigurationException e) {

            fail("error: " + e.getClass().getSimpleName());
        }
    }

}
