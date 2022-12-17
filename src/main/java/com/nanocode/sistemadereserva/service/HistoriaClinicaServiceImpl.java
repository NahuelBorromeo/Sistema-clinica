package com.nanocode.sistemadereserva.service;

import com.nanocode.sistemadereserva.entity.HistoriaClinica;
import com.nanocode.sistemadereserva.repository.HistoriaClinicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HistoriaClinicaServiceImpl implements HistoriaClinicaService {

    @Autowired
    private HistoriaClinicaRepository historiaClinicaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<HistoriaClinica> findAll() {
        return (List<HistoriaClinica>) historiaClinicaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<HistoriaClinica> findAll(Pageable pageable) {
        return historiaClinicaRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public void save(HistoriaClinica historiaClinica) {
        historiaClinicaRepository.save(historiaClinica);
    }

    @Override
    @Transactional(readOnly = true)
    public HistoriaClinica findOne(Long id) {
        return historiaClinicaRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        historiaClinicaRepository.deleteById(id);
    }
}
