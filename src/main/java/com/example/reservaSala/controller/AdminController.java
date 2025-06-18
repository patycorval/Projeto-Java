package com.example.reservaSala.controller;

import com.example.reservaSala.model.Admin;
import com.example.reservaSala.model.Sala;
import com.example.reservaSala.service.AdminService;
import com.example.reservaSala.service.SalaService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

// import com.example.reservaSala.repositories.SalaRepository;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Exibir tela de login (o Spring Security que vai processar o login
    // automaticamente)
    @GetMapping("/login")
    public String exibirLogin() {
        return "login";
    }

    // Exibir tela de cadastro
    @GetMapping("/cadastro")
    public String exibirCadastro() {
        return "cadastro";
    }

    // Processar cadastro de novo admin
    @PostMapping("/cadastro")
    public String processarCadastro(@RequestParam String usuario,
            @RequestParam String senha,
            Model model) {
        // Criptografar a senha antes de salvar
        String senhaCriptografada = passwordEncoder.encode(senha);

        Admin novoAdmin = new Admin();
        novoAdmin.setUsuario(usuario);
        novoAdmin.setSenha(senhaCriptografada);

        adminService.salvar(novoAdmin);

        model.addAttribute("mensagem", "Admin cadastrado com sucesso!");
        return "redirect:/admin/login";
    }

    @Autowired
    private SalaService salaService;

    @GetMapping("/principal")
    public String principal(Model model) {
        // Busca todas as salas
        List<Sala> todasSalas = salaService.listarTodas();

        // Filtra salas do andar 3 (assumindo que o local contém "3")
        List<Sala> salasAndar3 = todasSalas.stream()
                .filter(sala -> sala.getLocalizacao() != null && sala.getLocalizacao().contains("3"))
                .toList();

        // Filtra salas do andar 5 (assumindo que o local contém "5")
        List<Sala> salasAndar5 = todasSalas.stream()
                .filter(sala -> sala.getLocalizacao() != null && sala.getLocalizacao().contains("5"))
                .toList();

        // Adiciona os dados ao modelo para a view
        model.addAttribute("salasAndar3", salasAndar3);
        model.addAttribute("salasAndar5", salasAndar5);
        model.addAttribute("usuario", "admin");

        // Define a página ativa para o header
        model.addAttribute("activePage", "principal");

        return "principal";
    }
}
