package com.nanocode.sistemadereserva.service;

import com.nanocode.sistemadereserva.entity.Medico;
import com.nanocode.sistemadereserva.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MedicoServiceImpl implements MedicoService {

    @Autowired
    private MedicoRepository medicoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Medico> findAll() {
        return (List<Medico>) medicoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Medico> findAll(Pageable pageable) {
        return medicoRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public void save(Medico medico) {
        medicoRepository.save(medico);
    }

    @Override
    @Transactional(readOnly = true)
    public Medico findOne(Long id) {
        return medicoRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        medicoRepository.deleteById(id);
    }
}
