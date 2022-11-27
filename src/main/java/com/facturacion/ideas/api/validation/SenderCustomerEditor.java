package com.facturacion.ideas.api.validation;
import com.facturacion.ideas.api.dto.SenderNewDTO;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

@Component
public class SenderCustomerEditor extends PropertyEditorSupport {

    private static final Logger LOGGER = LogManager.getLogger(SenderCustomerEditor.class);

    @Override
    public void setAsText(String text) throws IllegalArgumentException {

        LOGGER.info("Dato: " + text + "  " +  text.length());

        SenderNewDTO senderConvert = new Gson().fromJson(text, SenderNewDTO.class);


        this.setValue(senderConvert);
    }
}
