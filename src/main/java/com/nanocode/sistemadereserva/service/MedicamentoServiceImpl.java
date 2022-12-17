package com.nanocode.sistemadereserva.service;

import com.nanocode.sistemadereserva.entity.Medicamento;
import com.nanocode.sistemadereserva.repository.MedicamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MedicamentoServiceImpl implements MedicamentoService {

    @Autowired
    private MedicamentoRepository medicamentoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Medicamento> findAll() {
        return (List<Medicamento>) medicamentoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Medicamento> findAll(Pageable pageable) {
        return medicamentoRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public void save(Medicamento medicamento) {
        medicamentoRepository.save(medicamento);
    }

    @Override
    @Transactional(readOnly = true)
    public Medicamento findOne(Long id) {
        return medicamentoRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        medicamentoRepository.deleteById(id);
    }
}
