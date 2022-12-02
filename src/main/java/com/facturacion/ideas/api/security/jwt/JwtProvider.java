package com.facturacion.ideas.api.security.jwt;

import com.facturacion.ideas.api.security.entity.UserMain;
import io.jsonwebtoken.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {

    private static final Logger LOGGER = LogManager.getLogger(JwtProvider.class);

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private int expiration;


    private Long da = 30000000L;

    public String generateToken(Authentication authentication) {

        UserMain userMain = (UserMain) authentication.getPrincipal();

        return Jwts.builder().setSubject(userMain.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + da))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String getRucFromToken(String token) {
        // EL subjet es el nombre de usuario,en nuestri caso es el ruc
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token) {
        try {

            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e) {
            LOGGER.error("error token mal formado " + token, e);

        } catch (UnsupportedJwtException e) {
            LOGGER.error("error token no soportada " + token, e);

        } catch (ExpiredJwtException e) {
            LOGGER.error("error token expirado " + token, e);
        } catch (IllegalArgumentException e) {
            LOGGER.error("error token vacio " + token, e);
        } catch (SignatureException e) {

            LOGGER.error("error token firma " + token, e);
        }
        return  false;

    }
}
