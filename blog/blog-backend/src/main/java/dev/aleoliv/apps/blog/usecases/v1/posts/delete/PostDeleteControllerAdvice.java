package dev.aleoliv.apps.blog.usecases.v1.posts.delete;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PostDeleteControllerAdvice {

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<String> handle(RuntimeException e) {
		return ResponseEntity.badRequest().body(e.getMessage());
	}
}
