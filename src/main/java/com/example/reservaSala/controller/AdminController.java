package com.example.reservaSala.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    public static final String ATRIBUTO_ADMIN = "isAdmin";

    @GetMapping("/login")
    public String exibirLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String processarLogin(@RequestParam String usuario,
            @RequestParam String senha,
            HttpSession session,
            Model model) {
        if ("admin".equals(usuario) && "123".equals(senha)) {
            session.setAttribute(ATRIBUTO_ADMIN, true);
            return "redirect:/admin/principal";
        } else {
            model.addAttribute("erro", "Usuário ou senha inválidos.");
            return "login";
        }
    }

    @GetMapping("/cadastro")
    public String exibirCadastro() {
        return "cadastro";
    }

    @PostMapping("/cadastro")
    public String processarCadastro(@RequestParam String nome,
            @RequestParam String email,
            @RequestParam String senha,
            Model model) {
        // logica pra salvar o usuario aqui
        model.addAttribute("mensagem", "Usuário cadastrado com sucesso!");

        return "redirect:/admin/login";
    }

    @GetMapping("/principal")
    public String principal(HttpSession session, Model model) {
        Boolean autenticado = (Boolean) session.getAttribute(ATRIBUTO_ADMIN);

        if (Boolean.TRUE.equals(autenticado)) {
            model.addAttribute("activePage", "principal");
            model.addAttribute("usuario", "admin");
            return "principal";
        } else {
            return "redirect:/admin/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/admin/login";
    }
}
