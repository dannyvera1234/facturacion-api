package com.facturacion.ideas.api.security.jwt;

import com.facturacion.ideas.api.security.dto.JwtDTO;
import com.facturacion.ideas.api.security.entity.UserMain;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import io.jsonwebtoken.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtProvider {

    private static final Logger LOGGER = LogManager.getLogger(JwtProvider.class);

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String generateToken(Authentication authentication) {

        UserMain userMain = (UserMain) authentication.getPrincipal();

        List<String> roles = userMain.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());


        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", roles);
        claims.put("emisor", userMain.getNombreEmisor());

        return Jwts.builder().setSubject(userMain.getUsername())
                //.claim("roles", roles)
                .addClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expiration))
                .signWith(SignatureAlgorithm.HS512, secret.getBytes())
                .compact();
    }

    public String getRucFromToken(String token) {
        // EL subjet es el nombre de usuario,en nuestri caso es el ruc
        return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token) {
        try {

            Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token);
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
        return false;

    }

    public  String refreshToken(JwtDTO jwtDTO) throws ParseException {


        JWT jwt = JWTParser.parse(jwtDTO.getToken());

        JWTClaimsSet claimsSet = jwt.getJWTClaimsSet();

        String userName = claimsSet.getSubject();

        Map<String, Object> claims = claimsSet.getClaims();
        LOGGER.info("Refresh " +claims);
        return Jwts.builder().setSubject(userName)
                //.claim("roles", roles)
                .addClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expiration))
                .signWith(SignatureAlgorithm.HS512, secret.getBytes())
                .compact();

    }
}
