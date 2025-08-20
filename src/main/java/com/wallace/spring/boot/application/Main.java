package com.wallace.spring.boot.application;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.wallace.spring.boot.model.entities.User;
import com.wallace.spring.boot.model.repository.UserRepository;

@Component
public class Main implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void run(String... args) throws Exception {
		if (userRepository.count() == 0) {
			User managerUser = new User();
			managerUser.setUserName("user");
			managerUser.setPassword(passwordEncoder.encode("1234"));
			managerUser.setRoles(Arrays.asList("MANAGER"));
			userRepository.save(managerUser);

			User commonUser = new User();
			commonUser.setUserName("admin");
			commonUser.setPassword(passwordEncoder.encode("0987"));
			commonUser.setRoles(Arrays.asList("USER"));
			userRepository.save(commonUser);

			System.out.println("Usuários 'fulano' (MANAGER) e 'ciclano' (USER) criados com sucesso!");
		}
		
		
	}
}
