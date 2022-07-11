package dev.aleoliv.apps.blog.usecases.v1.posts.delete;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/posts")
public class PostsDeleteController {

	private final PostsDeleteService postsDeleteService;

	public PostsDeleteController(PostsDeleteService postsDeleteService) {
		this.postsDeleteService = postsDeleteService;
	}

	@DeleteMapping(value = "{id}")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<?> handle(@PathVariable("id") UUID id, @RequestHeader("Authorization") String token)
			throws Exception {
		postsDeleteService.execute(id, token);
		return ResponseEntity.noContent().build();
	}
}
