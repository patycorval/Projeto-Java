// package com.example.reservaSala;

// import org.springframework.boot.SpringApplication;
// import org.springframework.boot.autoconfigure.SpringBootApplication;

// @SpringBootApplication
// public class ReservaSalaApplication {

// 	public static void main(String[] args) {
// 		SpringApplication.run(ReservaSalaApplication.class, args);
// 	}

// }

package com.example.reservaSala;

import com.example.reservaSala.model.Sala;
import com.example.reservaSala.model.enums.Recurso;
import com.example.reservaSala.model.enums.TipoSala;
import com.example.reservaSala.service.SalaService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class ReservaSalaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReservaSalaApplication.class, args);
	}

	@Bean
	public CommandLineRunner carregarSalas(SalaService salaService) {
		return args -> {
			// Apenas se o banco estiver vazio (evita duplicar a cada execução)
			if (salaService.listarTodas().isEmpty()) {
				List<Recurso> recursos = List.of(Recurso.PROJETOR, Recurso.TELEVISOR);

				salaService.salvar(new Sala("301", 35, "3º Andar", TipoSala.SALA_AULA, true, recursos));
				salaService.salvar(new Sala("302", 40, "3º Andar", TipoSala.LABORATORIO, true, recursos));
				salaService.salvar(new Sala("303", 25, "3º Andar", TipoSala.SALA_AULA, true, recursos));

				salaService.salvar(new Sala("501", 30, "5º Andar", TipoSala.LABORATORIO, true, recursos));
				salaService.salvar(new Sala("502", 45, "5º Andar", TipoSala.LABORATORIO, true, recursos));
				salaService.salvar(new Sala("503", 50, "5º Andar", TipoSala.SALA_AULA, true, recursos));
			}
		};
	}
}
