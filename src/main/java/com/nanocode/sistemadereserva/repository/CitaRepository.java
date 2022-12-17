package com.nanocode.sistemadereserva.repository;

import com.nanocode.sistemadereserva.entity.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface CitaRepository extends JpaRepository<Cita, Long>,
                                    PagingAndSortingRepository<Cita, Long> {

}
