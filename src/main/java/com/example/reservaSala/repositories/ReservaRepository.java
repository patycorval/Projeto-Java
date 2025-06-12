package com.example.reservaSala.repositories;

import java.time.LocalDate;
import java.util.List;
import com.example.reservaSala.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    List<Reserva> findByNumeroAndData(String numero, LocalDate data);
}
