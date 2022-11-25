package com.facturacion.ideas.api.validation;

import com.facturacion.ideas.api.dto.SenderNewDTO;
import com.google.gson.Gson;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

@Component
public class SenderCustomerEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {

        SenderNewDTO senderConvert = new Gson().fromJson(text, SenderNewDTO.class);

        System.out.println("CONvertido: " +senderConvert);
        this.setValue(senderConvert);
    }
}
