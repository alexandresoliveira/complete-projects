package dev.aleoliv.apps.blog.usecases.v1.authentication.login;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import dev.aleoliv.apps.blog.shared.configurations.security.TokenService;

@Service
public class AuthenticationLoginService {

	private final AuthenticationManager authenticationManager;
	private final TokenService tokenService;

	public AuthenticationLoginService(AuthenticationManager authenticationManager, TokenService tokenService) {
		this.authenticationManager = authenticationManager;
		this.tokenService = tokenService;
	}

	public AuthenticationLoginResponseDto execute(AuthenticationLoginRequestDto request) {
		UsernamePasswordAuthenticationToken data = new UsernamePasswordAuthenticationToken(request.getEmail(),
				request.getPassword());
		Authentication authenticate = authenticationManager.authenticate(data);
		String token = tokenService.buildToken(authenticate);
		return new AuthenticationLoginResponseDto(token, "Bearer");
	}

}
