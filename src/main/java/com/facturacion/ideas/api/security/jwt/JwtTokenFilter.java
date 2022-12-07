package com.facturacion.ideas.api.security.jwt;

import com.facturacion.ideas.api.security.service.UserDetailsServiceImpl;
import com.facturacion.ideas.api.util.ConstanteUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Se ejecuta una vez por cada peticion
 */
public class JwtTokenFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LogManager.getLogger(JwtTokenFilter.class);

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {

            String token = getToken(request);

            if (token != null && jwtProvider.validateToken(token)) {

                ConstanteUtil.TOKEN = token;

                // Obtener el usuario a partir del token
                String ruc = jwtProvider.getRucFromToken(token);

                ConstanteUtil.TOKEN_USER = ruc;

                // Obtener las autorizaciones
                UserDetails userDetails = userDetailsService.loadUserByUsername(ruc);

                //  Autenticar el usuario
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                // Asignar al contexto de autenticacion el usuario
                SecurityContextHolder.getContext().setAuthentication(auth);

            }

        } catch (Exception e) {

            //LOGGER.error(e.getMessage(), e);
        }


        filterChain.doFilter(request, response);

    }

    private String getToken(HttpServletRequest request) {

        // Obtenemos un atributo de la cabezera, en autoizacion se envia el token
        String header = request.getHeader("Authorization");

        // Bearer en siempre así
        if (header != null && header.startsWith("Bearer")) {

            // quitar el texto Bearer para dejar solo el valor del token
            return header.replace("Bearer", "");
        }

        return null;
    }
}
