package dev.aleoliv.apps.blog.usecases.v1.posts.index;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("v1/posts")
public class PostsIndexController {

	private final PostsIndexService postsIndexService;

	public PostsIndexController(PostsIndexService postsIndexService) {
		this.postsIndexService = postsIndexService;
	}

	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	@PreAuthorize("hasRole('ROLE_USER')")
	public Page<PostsIndexResponseDto> handle(
			@PageableDefault(page = 0, size = 10, sort = { "createdAt" }, direction = Direction.ASC) Pageable pageable,
			PostsIndexRequestDto request, 
			@RequestHeader("Authorization") String token,
			UriComponentsBuilder uriBuilder) throws Exception {
		return postsIndexService.execute(pageable, request, token, uriBuilder);
	}
}
