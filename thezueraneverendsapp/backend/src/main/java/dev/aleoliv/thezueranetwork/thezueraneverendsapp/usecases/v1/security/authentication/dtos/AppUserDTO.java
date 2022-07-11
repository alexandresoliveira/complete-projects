package dev.aleoliv.thezueranetwork.thezueraneverendsapp.usecases.v1.security.authentication.dtos;

import java.util.Collection;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class AppUserDTO extends User {

  private UUID id;

  public AppUserDTO(UUID id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
    super(username, password, authorities);
    this.id = id;
  }

  public UUID getId() {
    return id;
  }

}
