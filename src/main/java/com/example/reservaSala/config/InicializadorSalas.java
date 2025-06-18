package com.example.reservaSala.config;

import com.example.reservaSala.model.Sala;
import com.example.reservaSala.model.enums.TipoSala;
import com.example.reservaSala.model.enums.Recurso;
import com.example.reservaSala.service.SalaService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InicializadorSalas implements CommandLineRunner {

    private final SalaService salaService;

    public InicializadorSalas(SalaService salaService) {
        this.salaService = salaService;
    }

    @Override
    public void run(String... args) {
        salaService.apagarTodasSalas();

        List<Sala> salas = List.of(
                new Sala("3.01", 50, "3º andar", TipoSala.LABORATORIO, true,
                        List.of(Recurso.TELEVISOR)),
                new Sala("3.02", 40, "3º andar", TipoSala.LABORATORIO, true,
                        List.of(Recurso.TELEVISOR)),
                new Sala("3.03", 45, "3º andar", TipoSala.LABORATORIO, true,
                        List.of(Recurso.PROJETOR)),
                new Sala("3.04", 60, "3º andar", TipoSala.LABORATORIO, true,
                        List.of(Recurso.TELEVISOR)),
                new Sala("3.05", 60, "3º andar", TipoSala.LABORATORIO, true,
                        List.of(Recurso.TELEVISOR)),

                // 5º andar
                new Sala("501", 50, "5º andar", TipoSala.SALA_AULA, true,
                        List.of(Recurso.TELEVISOR)),
                new Sala("502", 40, "5º andar", TipoSala.SALA_AULA, true,
                        List.of(Recurso.TELEVISOR)),
                new Sala("503", 45, "5º andar", TipoSala.SALA_AULA, true,
                        List.of(Recurso.PROJETOR)),
                new Sala("504", 60, "5º andar", TipoSala.SALA_AULA, true,
                        List.of(Recurso.TELEVISOR)),
                new Sala("505", 60, "5º andar", TipoSala.SALA_AULA, true,
                        List.of(Recurso.TELEVISOR)));

        salaService.salvarTodas(salas);
        System.out.println("✅ Salas pré-definidas carregadas com sucesso.");
    }
}
