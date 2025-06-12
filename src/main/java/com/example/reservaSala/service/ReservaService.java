package com.example.reservaSala.service;

import com.example.reservaSala.model.Reserva;
import com.example.reservaSala.repositories.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalTime;

import java.util.List;
import java.util.Optional;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    public boolean temConflito(Reserva novaReserva) {
        List<Reserva> reservasNoMesmoDia = reservaRepository.findByNumeroAndData(novaReserva.getNumero(),
                novaReserva.getData());

        LocalTime inicioNova = novaReserva.getHora();
        LocalTime fimNova = inicioNova.plusHours(novaReserva.getDuracao());

        for (Reserva r : reservasNoMesmoDia) {
            LocalTime inicioExistente = r.getHora();
            LocalTime fimExistente = inicioExistente.plusHours(r.getDuracao());

            boolean conflito = inicioNova.isBefore(fimExistente) && fimNova.isAfter(inicioExistente);
            if (conflito) {
                return true;
            }
        }

        return false;
    }

    public boolean temConflitoAtualizacao(Reserva novaReserva) {
        List<Reserva> reservasNoMesmoDia = reservaRepository.findByNumeroAndData(novaReserva.getNumero(),
                novaReserva.getData());

        LocalTime inicioNova = novaReserva.getHora();
        LocalTime fimNova = inicioNova.plusHours(novaReserva.getDuracao());

        for (Reserva r : reservasNoMesmoDia) {
            // Ignorar a própria reserva que está sendo atualizada
            if (novaReserva.getId() != null && r.getId().equals(novaReserva.getId())) {
                continue;
            }

            LocalTime inicioExistente = r.getHora();
            LocalTime fimExistente = inicioExistente.plusHours(r.getDuracao());

            boolean conflito = inicioNova.isBefore(fimExistente) && fimNova.isAfter(inicioExistente);
            if (conflito) {
                return true;
            }
        }

        return false;
    }

    public Reserva salvar(Reserva reserva) {
        if (temConflito(reserva)) {
            throw new IllegalArgumentException("Já existe uma reserva para essa sala nesse horário.");
        }
        return reservaRepository.save(reserva);
    }

    public Reserva atualizar(Reserva reserva) {
        if (temConflitoAtualizacao(reserva)) {
            throw new IllegalArgumentException("Já existe uma reserva para essa sala nesse horário.");
        }
        return reservaRepository.save(reserva);
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
