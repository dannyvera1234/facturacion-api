package com.facturacion.ideas.api.xml;

import com.facturacion.ideas.api.dto.SenderNewDTO;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@Component
public class SenderInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER = LogManager.getLogger(SenderInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        LOGGER.info("Interceptor Sender");
        String data = request.getParameter("senderNewDTO");

        SenderNewDTO senderNewDTO = new Gson().fromJson(data, SenderNewDTO.class);
        LOGGER.info("Interceptor : " + senderNewDTO);

        request.setAttribute("pedro", senderNewDTO);

        return true;
    }

}
