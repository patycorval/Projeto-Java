package com.example.reservaSala.service;

import com.example.reservaSala.model.Admin;
import com.example.reservaSala.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// import java.util.Optional;

@Service
public class AdminService {

    @Autowired // injeta o adminRepository, posso usar os métodos
    private AdminRepository adminRepository;

    // cadastrar admin
    public Admin salvar(Admin admin) {
        return adminRepository.save(admin);
    }

}
