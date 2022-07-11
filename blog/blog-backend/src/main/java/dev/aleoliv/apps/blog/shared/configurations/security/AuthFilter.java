package dev.aleoliv.apps.blog.shared.configurations.security;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import dev.aleoliv.apps.blog.shared.database.entities.UserEntity;
import dev.aleoliv.apps.blog.shared.database.repositories.UserRepository;

public class AuthFilter extends OncePerRequestFilter {
	
	private final TokenService tokenService;
	private final UserRepository userRepository;

	public AuthFilter(TokenService tokenService, UserRepository userRepository) {
		this.tokenService = tokenService;
		this.userRepository = userRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = recoverToken(request);
		if (tokenService.isValidToken(token)) {
			try {
				authorizationClient(token);
			} catch (Exception e) {
				throw new ServletException(e);
			}
		}
		filterChain.doFilter(request, response);
	}

	private void authorizationClient(String token) throws Exception {
		UUID id = tokenService.recoverUserId(token);
		UserEntity userEntity = userRepository.findById(id).get();
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userEntity, null, userEntity.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	private String recoverToken(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		if (header == null || header.isEmpty() || !header.startsWith("Bearer "))
			return null;
		return header.substring(7);
	}

}
