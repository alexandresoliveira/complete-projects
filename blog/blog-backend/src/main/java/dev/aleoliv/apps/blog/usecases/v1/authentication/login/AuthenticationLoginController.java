package dev.aleoliv.apps.blog.usecases.v1.authentication.login;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/auth/login")
public class AuthenticationLoginController {

	private final AuthenticationLoginService service;

	public AuthenticationLoginController(AuthenticationLoginService service) {
		this.service = service;
	}
	
	@PostMapping
	public ResponseEntity<AuthenticationLoginResponseDto> handle(@RequestBody @Valid AuthenticationLoginRequestDto request) {
		AuthenticationLoginResponseDto responseDto = service.execute(request);
		return ResponseEntity.ok(responseDto);
	}
}
