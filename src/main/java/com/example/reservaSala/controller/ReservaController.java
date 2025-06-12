package com.example.reservaSala.controller;

import com.example.reservaSala.model.Reserva;
import com.example.reservaSala.service.ReservaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @GetMapping("/")
    public String paginaPrincipal(HttpSession session) {
        Boolean autenticado = (Boolean) session.getAttribute("isAdmin");
        if (!Boolean.TRUE.equals(autenticado)) {
            return "redirect:/admin/login";
        }
        return "principal";
    }

    // Formulário de reserva (apenas admin pode reservar)
    @GetMapping("/reservar")
    public String exibirFormulario(@RequestParam String numero, Model model, HttpSession session) {
        Boolean autenticado = (Boolean) session.getAttribute(AdminController.ATRIBUTO_ADMIN);
        if (!Boolean.TRUE.equals(autenticado)) {
            return "redirect:/admin/login";
        }

        Reserva reserva = new Reserva();
        reserva.setNumero(numero);
        model.addAttribute("reserva", reserva);
        return "reservar";
    }

    // Salvar reserva (restrito a admin)
    @PostMapping("/reservar")
    public String realizarReserva(@ModelAttribute Reserva reserva, Model model, HttpSession session) {
        Boolean autenticado = (Boolean) session.getAttribute(AdminController.ATRIBUTO_ADMIN);
        if (!Boolean.TRUE.equals(autenticado)) {
            return "redirect:/admin/login";
        }

        reservaService.salvar(reserva);
        model.addAttribute("reserva", reserva);
        return "sucesso";
    }

    // Listagem de reservas (restrito a admin)
    @GetMapping("/listagem")
    public String listarReservas(Model model, HttpSession session) {
        Boolean autenticado = (Boolean) session.getAttribute(AdminController.ATRIBUTO_ADMIN);
        if (!Boolean.TRUE.equals(autenticado)) {
            return "redirect:/admin/login";
        }

        model.addAttribute("reservas", reservaService.listarTodas());
        return "listagem";
    }

    // Deletar reserva (restrito a admin)
    @PostMapping("/deletar/{id}")
    public String deletarReserva(@PathVariable Long id, HttpSession session) {
        Boolean autenticado = (Boolean) session.getAttribute(AdminController.ATRIBUTO_ADMIN);
        if (!Boolean.TRUE.equals(autenticado)) {
            return "redirect:/admin/login";
        }

        reservaService.deletar(id);
        return "redirect:/listagem";
    }

    @GetMapping("/reservas/editar/{id}")
    public String exibirFormularioEdicao(@PathVariable Long id, Model model, HttpSession session) {
        Boolean autenticado = (Boolean) session.getAttribute(AdminController.ATRIBUTO_ADMIN);
        if (!Boolean.TRUE.equals(autenticado)) {
            return "redirect:/admin/login";
        }

        Optional<Reserva> optionalReserva = reservaService.buscarPorId(id);
        if (optionalReserva.isEmpty()) {
            return "redirect:/listagem";
        }

        Reserva reserva = optionalReserva.get();
        model.addAttribute("reserva", reserva);
        return "atualizar";
    }

    @PostMapping("/reservas/editar/{id}")
    public String atualizarReserva(@PathVariable Long id, @ModelAttribute Reserva reservaAtualizada,
            HttpSession session) {
        Boolean autenticado = (Boolean) session.getAttribute(AdminController.ATRIBUTO_ADMIN);
        if (!Boolean.TRUE.equals(autenticado)) {
            return "redirect:/admin/login";
        }

        Optional<Reserva> optionalReserva = reservaService.buscarPorId(id);
        if (optionalReserva.isEmpty()) {
            return "redirect:/listagem";
        }

        Reserva reservaExistente = optionalReserva.get();
        reservaExistente.setNome(reservaAtualizada.getNome());
        reservaExistente.setData(reservaAtualizada.getData());
        reservaExistente.setHora(reservaAtualizada.getHora());
        reservaExistente.setDuracao(reservaAtualizada.getDuracao());
        // O número da sala continua o mesmo

        reservaService.salvar(reservaExistente);
        return "redirect:/listagem";
    }

    @GetMapping("/contato")
    public String paginaContato() {
        return "contato";
    }
}
