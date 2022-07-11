package dev.aleoliv.apps.blog.usecases.v1.authentication.login;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AuthenticationLoginRequestDto {

	@NotNull
	@NotEmpty
	@Email
	private final String email;
	
	@NotNull
	@NotEmpty
	@Size(min = 6)
	private final String password;
}
