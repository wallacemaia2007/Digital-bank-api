package com.wallace.spring.boot.application;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.wallace.spring.boot.enums.Role;
import com.wallace.spring.boot.model.entities.User;
import com.wallace.spring.boot.model.repository.UserRepository;

@Component
public class Main implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Main(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        
        if (userRepository.findByEmail("admin@admin.com").isEmpty()) {
            User admin = User.builder()
                .username("Administrador")
                .sobrenome("Sistema")
                .email("admin@admin.com")
                .password(passwordEncoder.encode("admin123"))
                .roles(Role.ADMIN)
                .build();
            
            userRepository.save(admin);
            System.out.println("Usuário ADMIN criado:");
            System.out.println("   Email: admin@admin.com");
            System.out.println("   Senha: admin123");
        }

        if (userRepository.findByEmail("user@user.com").isEmpty()) {
            User user = User.builder()
                .username("Usuário")
                .sobrenome("Comum")
                .email("user@user.com")
                .password(passwordEncoder.encode("user123"))
                .roles(Role.USER)
                .build();
            
            userRepository.save(user);
            System.out.println("Usuário USER criado:");
            System.out.println("   Email: user@user.com");
            System.out.println("   Senha: user123");
        }

        System.out.println("\nSistema de Permissões:");
        System.out.println("   ADMIN: Pode fazer tudo (inclusive deletar e alterar dados)");
        System.out.println("   USER: Pode ler e criar");
    }
}