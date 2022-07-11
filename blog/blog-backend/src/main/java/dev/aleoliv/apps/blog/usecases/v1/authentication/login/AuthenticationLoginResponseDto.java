package dev.aleoliv.apps.blog.usecases.v1.authentication.login;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AuthenticationLoginResponseDto {

	private final String token;
	private final String type;
}
