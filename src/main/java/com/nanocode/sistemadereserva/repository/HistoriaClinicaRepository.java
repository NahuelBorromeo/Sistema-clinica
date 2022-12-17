package com.nanocode.sistemadereserva.repository;

import com.nanocode.sistemadereserva.entity.HistoriaClinica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface HistoriaClinicaRepository extends JpaRepository<HistoriaClinica, Long>,
                                    PagingAndSortingRepository<HistoriaClinica, Long> {

}
