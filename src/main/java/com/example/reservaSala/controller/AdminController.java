package com.example.reservaSala.controller;

import com.example.reservaSala.model.Admin;
import com.example.reservaSala.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Exibir tela de login (o Spring Security que vai processar o login automaticamente)
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

    // Página principal do admin (acesso só pra ROLE_ADMIN)
    @GetMapping("/principal")
    public String principal(Model model) {
        model.addAttribute("usuario", "admin");  // Só pra exibir no HTML se quiser
        model.addAttribute("activePage", "principal");
        return "principal";
    }

}
