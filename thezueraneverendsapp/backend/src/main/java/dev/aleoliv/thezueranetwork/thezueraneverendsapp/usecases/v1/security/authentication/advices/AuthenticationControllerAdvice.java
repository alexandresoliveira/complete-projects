package dev.aleoliv.thezueranetwork.thezueraneverendsapp.usecases.v1.security.authentication.advices;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import dev.aleoliv.thezueranetwork.thezueraneverendsapp.shared.exceptions.ControllerApiException;
import dev.aleoliv.thezueranetwork.thezueraneverendsapp.shared.exceptions.ServiceApiException;

@RestControllerAdvice
public class AuthenticationControllerAdvice {

  @ExceptionHandler(ServiceApiException.class)
  public ResponseEntity<String> handlerServiceApiException(ServiceApiException e) {
    return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ControllerApiException.class)
  public ResponseEntity<String> handlerControllerApiException(ControllerApiException e) {
    return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
  }
}
