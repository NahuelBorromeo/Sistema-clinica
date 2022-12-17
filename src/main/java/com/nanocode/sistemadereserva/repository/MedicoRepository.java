package com.nanocode.sistemadereserva.repository;
import com.nanocode.sistemadereserva.entity.Medico;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MedicoRepository extends PagingAndSortingRepository<Medico, Long> {

}
