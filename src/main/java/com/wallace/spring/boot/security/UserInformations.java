package com.wallace.spring.boot.security;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.wallace.spring.boot.exceptions.UsuarioNaoEncontradoException;
import com.wallace.spring.boot.model.entities.User;
import com.wallace.spring.boot.model.repository.UserRepository;

@Service
public class UserInformations implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	public UserInformations(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = userRepository.findByUserName(userName)
				.orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado: " + userName));

		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
				user.getRoles().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role))
						.collect(Collectors.toList()));
	}
}
