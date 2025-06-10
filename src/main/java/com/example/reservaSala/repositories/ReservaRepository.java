package com.example.reservaSala.repositories;

import com.example.reservaSala.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    // Aqui você pode criar consultas personalizadas, se quiser
}
