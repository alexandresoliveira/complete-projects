package dev.aleoliv.apps.blog.usecases.v1.users.create;

import java.util.Set;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UsersCreateResponseDto {

	private UUID id;
	private String name;
	private String email;
	private Set<String> authorities;
}
