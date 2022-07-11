package dev.aleoliv.apps.blog.usecases.v1.comments.create;

import java.net.URI;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("v1/posts/{postId}/comments")
public class CommentsCreateController {
	
	private final CommentsCreateService service;

	public CommentsCreateController(CommentsCreateService service) {
		this.service = service;
	}

	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<CommentsCreateResponseDto> handle(@RequestBody @Valid CommentsCreateRequestDto request,
			@PathVariable("postId") UUID postId,
			UriComponentsBuilder uriBuilder, @RequestHeader("Authorization") String token) throws Exception {
		CommentsCreateResponseDto response = service.execute(request, postId, token);
		URI uri = uriBuilder.path("/v1/{postId}/comments/{id}").buildAndExpand(postId, response.getId()).toUri();
		return ResponseEntity.created(uri).body(response);
	}
}
