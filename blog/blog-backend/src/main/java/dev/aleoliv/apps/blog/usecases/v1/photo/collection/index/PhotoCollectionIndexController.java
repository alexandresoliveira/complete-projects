package dev.aleoliv.apps.blog.usecases.v1.photo.collection.index;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("v1/photo/collection")
public class PhotoCollectionIndexController {

	private final PhotoCollectionIndexService service;

	public PhotoCollectionIndexController(PhotoCollectionIndexService service) {
		this.service = service;
	}
	
	@GetMapping(value = "{userId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	@PreAuthorize("hasRole('ROLE_USER')")
	public Page<PhotoCollectionIndexResponseDto> handle(
			@PageableDefault(page = 0, size = 10, sort = { "createdAt" }, direction = Direction.ASC) Pageable pageable,
			PhotoCollectionIndexRequestDto request, 
			@PathVariable("userId") UUID userId,
			@RequestHeader("Authorization") String token,
			UriComponentsBuilder uriBuilder) throws Exception {
		return service.execute(pageable, request, userId, token, uriBuilder);
	}
	
	@GetMapping(value = "{userId}/{photoCollectionId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	@PreAuthorize("hasRole('ROLE_USER')")
	public PhotoCollectionIndexResponseDto handle(
			@PathVariable("userId") UUID userId,
			@PathVariable("photoCollectionId") UUID photoCollectionId,
			@RequestHeader("Authorization") String token,
			UriComponentsBuilder uriBuilder) throws Exception {
		return service.execute(photoCollectionId, userId, uriBuilder);
	}
}
