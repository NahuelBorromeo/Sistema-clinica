package com.nanocode.sistemadereserva.repository;

import com.nanocode.sistemadereserva.entity.Paciente;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PacienteRepository extends PagingAndSortingRepository<Paciente, Long> {

}
