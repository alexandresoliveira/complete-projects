package dev.aleoliv.thezueranetwork.thezueraneverendsapp.usecases.v1.security.authentication.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserDTO {
  private final String name;
  private final String email;
}
