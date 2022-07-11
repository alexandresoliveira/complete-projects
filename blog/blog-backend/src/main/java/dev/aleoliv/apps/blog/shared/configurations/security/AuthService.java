package dev.aleoliv.apps.blog.shared.configurations.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import dev.aleoliv.apps.blog.shared.database.repositories.UserRepository;

@Service
public class AuthService implements UserDetailsService {

	private final UserRepository repository;

	public AuthService(UserRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return repository
				.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("Username/Password are incorrect!"));
	}

}
