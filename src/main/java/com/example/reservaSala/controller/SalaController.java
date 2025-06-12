package com.example.reservaSala.controller;

import com.example.reservaSala.model.Sala;
// import com.example.reservaSala.model.enums.Recurso;
// import com.example.reservaSala.model.enums.TipoSala;
import com.example.reservaSala.service.SalaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/salas")
public class SalaController {

    @Autowired
    private SalaService salaService;

    // Listar todas as salas (inclusive inativas se quiser)
    @GetMapping
    public String listarSalas(Model model, HttpSession session) {
        if (!Boolean.TRUE.equals(session.getAttribute("isAdmin"))) {
            return "redirect:/admin/login";
        }

        List<Sala> salas = salaService.listarTodas();
        model.addAttribute("salas", salas);
        return "salas/listagem";
    }

    // Formulário para adicionar nova sala
    // @GetMapping("/nova")
    // public String exibirFormularioNova(Model model, HttpSession session) {
    // if (!Boolean.TRUE.equals(session.getAttribute("isAdmin"))) {
    // return "redirect:/admin/login";
    // }

    // model.addAttribute("sala", new Sala());
    // model.addAttribute("tipos", TipoSala.values());
    // model.addAttribute("recursosDisponiveis", Recurso.values());
    // return "salas/formulario";
    // }

    // // Salvar nova sala
    // @PostMapping("/salvar")
    // public String salvarSala(@ModelAttribute Sala sala, HttpSession session) {
    // if (!Boolean.TRUE.equals(session.getAttribute("isAdmin"))) {
    // return "redirect:/admin/login";
    // }

    // salaService.salvar(sala);
    // return "redirect:/salas";
    // }

    // // Editar sala existente
    // @GetMapping("/editar/{id}")
    // public String exibirFormularioEdicao(@PathVariable Long id, Model model,
    // HttpSession session) {
    // if (!Boolean.TRUE.equals(session.getAttribute("isAdmin"))) {
    // return "redirect:/admin/login";
    // }

    // Sala sala = salaService.buscarPorId(id);
    // if (sala == null) {
    // return "redirect:/salas";
    // }

    // model.addAttribute("sala", sala);
    // model.addAttribute("tipos", TipoSala.values());
    // model.addAttribute("recursosDisponiveis", Recurso.values());
    // return "salas/formulario";
    // }

    // // Desativar sala
    // @PostMapping("/desativar/{id}")
    // public String desativarSala(@PathVariable Long id, HttpSession session) {
    // if (!Boolean.TRUE.equals(session.getAttribute("isAdmin"))) {
    // return "redirect:/admin/login";
    // }

    // salaService.desativarSala(id);
    // return "redirect:/salas";
    // }
}
