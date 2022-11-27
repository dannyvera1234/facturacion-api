package com.facturacion.ideas.api.security.controller;

import com.facturacion.ideas.api.security.dto.JwtDTO;
import com.facturacion.ideas.api.security.dto.LoginUserDTO;
import com.facturacion.ideas.api.security.jwt.JwtProvider;
import com.facturacion.ideas.api.util.ConstanteUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(value = ConstanteUtil.CROOS_ORIGIN)
@RestController
@RequestMapping("/facturacion/auth")
public class AuthController {


    /**
     * tOKEN
     * eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMzA4NzU0MTk5MDAxIiwiaWF0IjoxNjY5NTA4MzE0LCJleHAiOjE2Njk1MDgzNTF9.H8HesuN8rXYCydAqQSxCL6wxV30szTy6NObfoNeqEu27ebpgEND99fikha5l6a0BZpuZfpnnsSyosOBkTIpJKg
     */

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtProvider jwtProvider;


    @PostMapping("/login")
    public ResponseEntity<JwtDTO> login(@Valid @RequestBody LoginUserDTO loginUserDTO) {

        // Autenticacion del usuario
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUserDTO.getRuc(), loginUserDTO.getPassword()));

        // Autenticar al usuario
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Crear el token
        String jwt = jwtProvider.generateToken(authentication);


        // Obtener el usuario principal
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // Crear la respuesta
        JwtDTO jwtDTO = new JwtDTO(jwt, userDetails.getUsername(), userDetails.getAuthorities());

        return ResponseEntity.ok(jwtDTO);

    }


}
