package dev.aleoliv.apps.blog.usecases.v1.authentication.login;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthenticationLoginControllerAdvice {

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<String> handle(BadCredentialsException e) {
		return ResponseEntity.badRequest().body("Wrong credentials, try again!");
	}
}
