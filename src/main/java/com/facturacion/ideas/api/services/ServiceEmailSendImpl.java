package com.facturacion.ideas.api.services;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.amazonaws.services.simpleemail.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceEmailSendImpl {
    private  static  final String MY_EMAIL ="desarrollandoideas8@gmail.com";
    @Autowired
    private AmazonSimpleEmailServiceClient client;

    public  void sendEmail(){
        Destination destination = new Destination();
        destination.withToAddresses("ronnychamba96@gmail.com");

        Message message = new Message();

        Content content = new Content();
        content.setData("Envio prueba facturacion");

        message.setSubject( content);

        Body body = new Body();

        body.setText( new Content("Mensaje enviado con exito"));
        message.setBody(body);


        SendEmailRequest emailRequest = new SendEmailRequest()
                .withSource(MY_EMAIL)
                .withDestination(destination)
                .withMessage(message);


        client.sendEmail(emailRequest);



    }
}
