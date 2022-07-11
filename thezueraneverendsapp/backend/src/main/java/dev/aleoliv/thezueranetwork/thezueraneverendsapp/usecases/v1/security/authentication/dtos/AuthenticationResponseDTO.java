package dev.aleoliv.thezueranetwork.thezueraneverendsapp.usecases.v1.security.authentication.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AuthenticationResponseDTO {

  private final String token;
  private final UserDTO user;
}
