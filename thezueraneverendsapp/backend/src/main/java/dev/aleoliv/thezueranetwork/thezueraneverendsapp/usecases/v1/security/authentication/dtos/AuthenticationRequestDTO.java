package dev.aleoliv.thezueranetwork.thezueraneverendsapp.usecases.v1.security.authentication.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AuthenticationRequestDTO {

  @NotBlank
  @Email
  private final String email;

  @NotBlank
  @Size(min = 6)
  private final String password;
}
