package dev.aleoliv.thezueranetwork.thezueraneverendsapp.usecases.v1.users.create.advices;

import dev.aleoliv.thezueranetwork.thezueraneverendsapp.shared.exceptions.ServiceApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CreateUsersControllerAdvice {

    @ExceptionHandler(ServiceApiException.class)
    public ResponseEntity<String> handlerServiceApiException(ServiceApiException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
