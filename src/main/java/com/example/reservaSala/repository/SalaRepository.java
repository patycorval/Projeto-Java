package com.example.reservaSala.repository;

import com.example.reservaSala.model.Sala;
import com.example.reservaSala.model.enums.Recurso;
import com.example.reservaSala.model.enums.TipoSala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalaRepository extends JpaRepository<Sala, Long> {

    // Personalizações úteis 

    List<Sala> findByAtivaTrue(); // Listar apenas salas ativas

    List<Sala> findByTipo(TipoSala tipo); // Filtrar por tipo de sala

    List<Sala> findByRecursosContaining(Recurso recurso); // Buscar salas com recurso específico (ex: "PROJETOR")
}