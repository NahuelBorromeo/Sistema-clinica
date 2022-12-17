package com.nanocode.sistemadereserva.repository;

import com.nanocode.sistemadereserva.entity.Medicamento;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MedicamentoRepository extends PagingAndSortingRepository<Medicamento, Long> {

}
