package dev.aleoliv.thezueranetwork.thezueraneverendsapp.usecases.v1.security.authentication.controllers;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import dev.aleoliv.thezueranetwork.thezueraneverendsapp.shared.exceptions.ControllerApiException;
import dev.aleoliv.thezueranetwork.thezueraneverendsapp.usecases.v1.security.authentication.dtos.AppUserDTO;
import dev.aleoliv.thezueranetwork.thezueraneverendsapp.usecases.v1.security.authentication.dtos.AuthenticationRequestDTO;
import dev.aleoliv.thezueranetwork.thezueraneverendsapp.usecases.v1.security.authentication.dtos.AuthenticationResponseDTO;
import dev.aleoliv.thezueranetwork.thezueraneverendsapp.usecases.v1.security.authentication.dtos.UserDTO;
import dev.aleoliv.thezueranetwork.thezueraneverendsapp.usecases.v1.security.authentication.services.JwtUserDetailsService;
import dev.aleoliv.thezueranetwork.thezueraneverendsapp.usecases.v1.security.authentication.utils.JwtTokenUtil;

@RestController
@RequestMapping(value = "v1/authentication")
public class AuthenticationController {

  private final AuthenticationManager authenticationManager;
  private final JwtTokenUtil jwtTokenUtil;
  private final JwtUserDetailsService userDetailsService;

  public AuthenticationController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil,
      JwtUserDetailsService userDetailsService) {
    this.authenticationManager = authenticationManager;
    this.jwtTokenUtil = jwtTokenUtil;
    this.userDetailsService = userDetailsService;
  }

  @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public ResponseEntity<AuthenticationResponseDTO> handle(@RequestBody @Valid AuthenticationRequestDTO requestDto) {
    authenticate(requestDto.getEmail(), requestDto.getPassword());

    final AppUserDTO appUser = (AppUserDTO) userDetailsService.loadUserByUsername(requestDto.getEmail());

    final String token = jwtTokenUtil.generateToken(appUser);

    final UserDTO userDTO = userDetailsService.loadUserInfo(appUser.getId());

    return ResponseEntity.ok(new AuthenticationResponseDTO(token, userDTO));
  }

  private void authenticate(String username, String password) {
    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    } catch (DisabledException e) {
      throw new ControllerApiException("Disable user");
    } catch (BadCredentialsException e) {
      throw new ControllerApiException("Credentials don't match");
    }
  }
}
