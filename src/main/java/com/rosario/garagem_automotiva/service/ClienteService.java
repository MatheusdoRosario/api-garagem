package com.rosario.garagem_automotiva.service;

import com.rosario.garagem_automotiva.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;
}
