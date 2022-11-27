package com.facturacion.ideas.api.security.service;

import com.facturacion.ideas.api.entities.Count;
import com.facturacion.ideas.api.repositories.ICountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class UserSeviceImpl implements IUserService {

    @Autowired
    private ICountRepository countRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<Count> getCountByRuc(String ruc) {
        return countRepository.findByRuc(ruc);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByRuc(String ruc) {

        return countRepository.existsByRuc(ruc);
    }
}
