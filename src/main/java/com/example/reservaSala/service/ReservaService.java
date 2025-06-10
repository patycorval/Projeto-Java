package com.example.reservaSala.service;

import com.example.reservaSala.model.Reserva;
import com.example.reservaSala.repositories.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    public Reserva salvar(Reserva reserva) {
        return reservaRepository.save(reserva);
        // Adc validações???
    }

    public List<Reserva> listarTodas() {
        return reservaRepository.findAll();
    }

    public Optional<Reserva> buscarPorId(Long id) {
        return reservaRepository.findById(id);
    }

    public void deletar(Long id) {
        reservaRepository.deleteById(id);
    }
}
