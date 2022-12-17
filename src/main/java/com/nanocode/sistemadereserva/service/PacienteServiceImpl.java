package com.nanocode.sistemadereserva.service;

import com.nanocode.sistemadereserva.entity.Paciente;
import com.nanocode.sistemadereserva.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PacienteServiceImpl implements PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Paciente> findAll() {
        return (List<Paciente>) pacienteRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Paciente> findAll(Pageable pageable) {
        return pacienteRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public void save(Paciente paciente) {
        pacienteRepository.save(paciente);
    }

    @Override
    @Transactional(readOnly = true)
    public Paciente findOne(Long id) {
        return pacienteRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        pacienteRepository.deleteById(id);
    }
}
