package com.facturacion.ideas.api.security.jwt;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class JwtEntryPoint  implements AuthenticationEntryPoint {


    private static final Logger LOGGER = LogManager.getLogger(JwtEntryPoint.class);
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        LOGGER.info("Error al el metodo commence");
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No autorizado");

    }
}
