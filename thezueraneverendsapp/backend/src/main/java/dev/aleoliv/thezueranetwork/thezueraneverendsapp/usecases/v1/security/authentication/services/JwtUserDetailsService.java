package dev.aleoliv.thezueranetwork.thezueraneverendsapp.usecases.v1.security.authentication.services;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import dev.aleoliv.thezueranetwork.thezueraneverendsapp.shared.database.entities.UserEntity;
import dev.aleoliv.thezueranetwork.thezueraneverendsapp.shared.exceptions.ServiceApiException;
import dev.aleoliv.thezueranetwork.thezueraneverendsapp.usecases.v1.security.authentication.dtos.AppUserDTO;
import dev.aleoliv.thezueranetwork.thezueraneverendsapp.usecases.v1.security.authentication.dtos.UserDTO;
import dev.aleoliv.thezueranetwork.thezueraneverendsapp.usecases.v1.security.authentication.repositories.AuthenticationRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {

  private final AuthenticationRepository authenticationRepository;

  public JwtUserDetailsService(AuthenticationRepository authenticationRepository) {
    this.authenticationRepository = authenticationRepository;
  }

  public UserDetails isValidUserTokenWith(UUID subject) {
    Optional<UserEntity> optionalUser = authenticationRepository.findById(subject);
    if (!optionalUser.isPresent()) {
      throw new UsernameNotFoundException("User not valid!");
    }
    UserEntity user = optionalUser.get();
    return new AppUserDTO(user.getId(), user.getEmail(), user.getPassword(), new ArrayList<>());
  }

  public UserDTO loadUserInfo(UUID id) {
    Optional<UserEntity> optionalUser = authenticationRepository.findById(id);
    if (!optionalUser.isPresent()) {
      throw new UsernameNotFoundException("User not found!");
    }
    UserEntity user = optionalUser.get();
    return new UserDTO(user.getName(), user.getEmail());
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Optional<UserEntity> optionalUser = authenticationRepository.findByEmail(email);
    if (!optionalUser.isPresent()) {
      throw new ServiceApiException("User not found with email: " + email);
    }
    UserEntity user = optionalUser.get();
    return new AppUserDTO(user.getId(), user.getEmail(), user.getPassword(), new ArrayList<>());
  }
}
