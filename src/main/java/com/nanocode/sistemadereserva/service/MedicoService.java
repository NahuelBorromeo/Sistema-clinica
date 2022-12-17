package com.nanocode.sistemadereserva.service;

import com.nanocode.sistemadereserva.entity.Medico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MedicoService {

    public List<Medico> findAll();

    public Page<Medico> findAll(Pageable pageable);

    public void save(Medico medico);

    public Medico findOne(Long id);

    public void delete(Long id);

}
