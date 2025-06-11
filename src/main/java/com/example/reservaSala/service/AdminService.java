package com.example.reservaSala.service;

import com.example.reservaSala.model.Admin;
import com.example.reservaSala.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    public Admin salvar(Admin admin) {
        return adminRepository.save(admin);
    }

    public Optional<Admin> autenticar(String usuario, String senha) {
        Optional<Admin> adminOpt = adminRepository.findByUsuario(usuario);
        if (adminOpt.isPresent()) {
            Admin admin = adminOpt.get();
            if (admin.getSenha().equals(senha)) {
                return Optional.of(admin);
            }
        }
        return Optional.empty();
    }
}
