package dev.aleoliv.apps.blog.usecases.v1.posts.create;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("v1/posts")
public class PostsCreateController {

	private final PostsCreateService postsCreateService;

	public PostsCreateController(PostsCreateService postsCreateService) {
		this.postsCreateService = postsCreateService;
	}

	@PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<PostsCreateResponseDto> handle(
			@RequestParam("file") MultipartFile file, 
			UriComponentsBuilder uriBuilder, 
			@RequestHeader("Authorization") String token) throws Exception {
		PostsCreateResponseDto response = postsCreateService.execute(file, token);
		URI uri = uriBuilder.path("/posts/{id}").path("/posts/{id}/comments")
				.buildAndExpand(response.getId(), response.getId()).toUri();
		return ResponseEntity.created(uri).body(response);
	}
	
	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<PostsCreateResponseDto> handle(
			@RequestBody @Valid PostsCreateRequestDto request,
			UriComponentsBuilder uriBuilder, 
			@RequestHeader("Authorization") String token) throws Exception {
		PostsCreateResponseDto response = postsCreateService.execute(request , token);
		URI uri = uriBuilder.path("/posts/{id}").path("/posts/{id}/comments")
				.buildAndExpand(response.getId(), response.getId()).toUri();
		return ResponseEntity.created(uri).body(response);
	}
}
