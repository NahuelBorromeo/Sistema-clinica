package com.nanocode.sistemadereserva.service;

import com.nanocode.sistemadereserva.entity.Cita;
import com.nanocode.sistemadereserva.repository.CitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CitaServiceImpl implements CitaService {

    @Autowired
    private CitaRepository citaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Cita> findAll() {
        return (List<Cita>) citaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Cita> findAll(Pageable pageable) {
        return citaRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public void save(Cita cita) {
        citaRepository.save(cita);
    }

    @Override
    @Transactional(readOnly = true)
    public Cita findOne(Long id) {
        return citaRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        citaRepository.deleteById(id);
    }
}
