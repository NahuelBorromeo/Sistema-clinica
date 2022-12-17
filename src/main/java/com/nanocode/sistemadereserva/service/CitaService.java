package com.nanocode.sistemadereserva.service;

import com.nanocode.sistemadereserva.entity.Cita;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CitaService {

    public List<Cita> findAll();

    public Page<Cita> findAll(Pageable pageable);

    public void save(Cita cita);

    public Cita findOne(Long id);

    public void delete(Long id);
}
