package com.nanocode.sistemadereserva.service;

import com.nanocode.sistemadereserva.entity.Paciente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PacienteService {

    public List<Paciente> findAll();

    public Page<Paciente> findAll(Pageable pageable);

    public void save(Paciente paciente);

    public Paciente findOne(Long id);

    public void delete(Long id);

}
