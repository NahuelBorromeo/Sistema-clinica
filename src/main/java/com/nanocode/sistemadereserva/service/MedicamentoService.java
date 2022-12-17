package com.nanocode.sistemadereserva.service;

import com.nanocode.sistemadereserva.entity.Medicamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MedicamentoService {

    public List<Medicamento> findAll();

    public Page<Medicamento> findAll(Pageable pageable);

    public void save(Medicamento medicamento);

    public Medicamento findOne(Long id);

    public void delete(Long id);

}
