package com.airline.reservation.config;

import com.airline.reservation.model.Role;
import com.airline.reservation.model.User;
import com.airline.reservation.repository.RoleRepository;
import com.airline.reservation.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class AdminSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.username:admin}")
    private String adminUsername;

    @Value("${app.admin.password:admin123}")
    private String adminPassword;

    @Override
    public void run(String... args) {
        Role adminRole = roleRepository.findByName("ROLE_ADMIN").orElseThrow();
        boolean adminExists = userRepository.findAll().stream()
                .anyMatch(u -> u.getRoles().contains(adminRole));
        if (adminExists) return;

        Role userRole = roleRepository.findByName("ROLE_USER").orElseThrow();
        User admin = new User();
        admin.setUsername(adminUsername);
        admin.setEmail(adminUsername + "@skylink.local");
        admin.setPassword(passwordEncoder.encode(adminPassword));
        admin.setRoles(Set.of(adminRole, userRole));
        userRepository.save(admin);
    }
}
