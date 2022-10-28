package com.facturacion.ideas.api.security.entity;

import com.facturacion.ideas.api.entities.Count;
import com.facturacion.ideas.api.entities.DetailsAggrement;
import com.facturacion.ideas.api.entities.Login;
import com.facturacion.ideas.api.entities.Sender;
import com.facturacion.ideas.api.enums.RolEnum;
import com.sun.istack.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UserMain implements UserDetails {

    private String ruc;

    private boolean estado;

    private Date fechaRegistro;
    private String password;

    // Para manejar la autorizacion de los usuarios
    private Collection<? extends GrantedAuthority>  authorities;

    public UserMain() {
    }

    public UserMain(String ruc, boolean estado, String password, Collection<? extends GrantedAuthority> authorities) {
        this.ruc = ruc;
        this.estado = estado;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserMain build(Count count){

        // Obtener los roles del usuario y convertir a autories
        List<GrantedAuthority> authoritiies= count.getRoles()
                                                    .stream()
                .map(rol -> new SimpleGrantedAuthority(rol.getRolNombreEnum().name()))
                .collect(Collectors.toList());
        
        return new UserMain(count.getRuc(), count.isEstado(), count.getPassword(), authoritiies);
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return ruc;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
