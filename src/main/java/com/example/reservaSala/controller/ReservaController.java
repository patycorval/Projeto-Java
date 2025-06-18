package com.example.reservaSala.controller;

import com.example.reservaSala.model.Reserva;
import com.example.reservaSala.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    // Apenas usuários autenticados podem acessar a página inicial
    @GetMapping("/")
    public String paginaPrincipal() {
        return "principal";
    }

    // Exibir formulário de reserva (somente ADMIN)
    @Secured("ROLE_ADMIN")
    @GetMapping("/reservar")
    public String exibirFormulario(@RequestParam String numero, Model model) {
        Reserva reserva = new Reserva();
        reserva.setNumero(numero);
        model.addAttribute("reserva", reserva);
        return "reservar";
    }

    // Salvar reserva (somente ADMIN)
    @Secured("ROLE_ADMIN")
    @PostMapping("/reservar")
    public String realizarReserva(@ModelAttribute Reserva reserva, Model model) {
        try {
            reservaService.salvar(reserva);
            model.addAttribute("reserva", reserva);
            return "sucesso";
        } catch (IllegalArgumentException e) {
            model.addAttribute("erro", e.getMessage());
            model.addAttribute("reserva", reserva);
            return "reservar";
        }
    }

    // Listar todas as reservas (somente ADMIN)
    @Secured("ROLE_ADMIN")
    @GetMapping("/listagem")
    public String listarReservas(Model model) {
        model.addAttribute("reservas", reservaService.listarTodas());
        model.addAttribute("activePage", "listagem");
        return "listagem";
    }

    // Deletar reserva (somente ADMIN)
    @Secured("ROLE_ADMIN")
    @PostMapping("/deletar/{id}")
    public String deletarReserva(@PathVariable Long id) {
        reservaService.deletar(id);
        return "redirect:/listagem";
    }

    // Exibir formulário de edição de reserva (somente ADMIN)
    @Secured("ROLE_ADMIN")
    @GetMapping("/reservas/editar/{id}")
    public String exibirFormularioEdicao(@PathVariable Long id, Model model) {
        Optional<Reserva> optionalReserva = reservaService.buscarPorId(id);
        if (optionalReserva.isEmpty()) {
            return "redirect:/listagem";
        }

        Reserva reserva = optionalReserva.get();
        model.addAttribute("reserva", reserva);
        return "atualizar";
    }

    // Atualizar reserva (somente ADMIN)
    @Secured("ROLE_ADMIN")
    @PostMapping("/reservas/editar/{id}")
    public String atualizarReserva(@PathVariable Long id,
            @ModelAttribute Reserva reservaAtualizada,
            Model model) {
        Optional<Reserva> optionalReserva = reservaService.buscarPorId(id);
        if (optionalReserva.isEmpty()) {
            return "redirect:/listagem";
        }

        Reserva reservaExistente = optionalReserva.get();

        reservaExistente.setNome(reservaAtualizada.getNome());
        reservaExistente.setData(reservaAtualizada.getData());
        reservaExistente.setHora(reservaAtualizada.getHora());
        reservaExistente.setDuracao(reservaAtualizada.getDuracao());

        try {
            reservaService.atualizar(reservaExistente);
        } catch (IllegalArgumentException e) {
            model.addAttribute("reserva", reservaExistente);
            model.addAttribute("erro", e.getMessage());
            return "atualizar";
        }

        return "redirect:/listagem";
    }

    // Página de contato (acesso livre)
    @GetMapping("/contato")
    public String paginaContato(Model model) {
        model.addAttribute("activePage", "contato");
        return "contato";
    }
}
