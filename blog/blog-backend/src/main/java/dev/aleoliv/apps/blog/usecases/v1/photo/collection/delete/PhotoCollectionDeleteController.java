package dev.aleoliv.apps.blog.usecases.v1.photo.collection.delete;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/photo/collection")
public class PhotoCollectionDeleteController {

	private final PhotoCollectionDeleteService service;

	public PhotoCollectionDeleteController(PhotoCollectionDeleteService service) {
		this.service = service;
	}

	@DeleteMapping(value = "{photoCollectionId}")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<?> handle(@PathVariable("photoCollectionId") UUID photoCollectionId,
			@RequestHeader("Authorization") String token) throws Exception {
		service.execute(photoCollectionId, token);
		return ResponseEntity.noContent().build();
	}
}
