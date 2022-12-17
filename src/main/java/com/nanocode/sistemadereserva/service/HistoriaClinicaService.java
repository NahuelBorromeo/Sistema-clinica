package com.nanocode.sistemadereserva.service;

import com.nanocode.sistemadereserva.entity.HistoriaClinica;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface HistoriaClinicaService {

    public List<HistoriaClinica> findAll();

    public Page<HistoriaClinica> findAll(Pageable pageable);

    public void save(HistoriaClinica historiaClinica);

    public HistoriaClinica findOne(Long id);

    public void delete(Long id);
}
