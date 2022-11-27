package com.facturacion.ideas.api.security.service;

import com.facturacion.ideas.api.entities.Count;
import com.facturacion.ideas.api.security.entity.UserMain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl  implements UserDetailsService {

    @Autowired
    private   IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String ruc) throws UsernameNotFoundException {
        /**
         * Si no lo encuentra, automaticamente lanzara una excepcion UsernameNotFoundException
         */

        Count count  = userService.getCountByRuc(ruc).get();
        // Construye el usuario
        return UserMain.build(count);

    }
}
