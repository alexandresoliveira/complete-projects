package dev.aleoliv.apps.blog.usecases.v1.comments.create;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommentsCreateControllerAdvice {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handle(Exception e) {
		return ResponseEntity.badRequest().body(e.getMessage());
	}
}
