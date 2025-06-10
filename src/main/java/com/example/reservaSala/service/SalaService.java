package com.example.reservaSala.service;

import com.example.reservaSala.model.Sala;
import com.example.reservaSala.repositories.SalaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SalaService {

    @Autowired
    private SalaRepository salaRepository;

    // ✅ Listar todas as salas
    public List<Sala> listarTodas() {
        return salaRepository.findAll();
    }

    // ✅ Buscar por ID
    public Sala buscarPorId(Long id) {
        Optional<Sala> optional = salaRepository.findById(id);
        return optional.orElse(null);
    }

    // ✅ Criar ou atualizar sala
    public Sala salvar(Sala sala) {
        return salaRepository.save(sala);
    }

    // ✅ Desativar sala
    public void desativarSala(Long id) {
        Sala sala = buscarPorId(id);
        if (sala != null) {
            sala.setAtiva(false);
            salaRepository.save(sala);
        }
    }
}
