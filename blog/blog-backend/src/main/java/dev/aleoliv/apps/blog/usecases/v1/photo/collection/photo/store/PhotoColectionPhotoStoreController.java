package dev.aleoliv.apps.blog.usecases.v1.photo.collection.photo.store;

import java.net.URI;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("v1/photo/collection/photo")
public class PhotoColectionPhotoStoreController {

	private final PhotoCollectionPhotoStoreService service;

	public PhotoColectionPhotoStoreController(PhotoCollectionPhotoStoreService service) {
		this.service = service;
	}

	@PostMapping(value = "{photoCollectionId}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<?> handle(@RequestParam("file") MultipartFile file,
			@RequestHeader("Authorization") String token, UriComponentsBuilder uriBuilder,
			@PathVariable("photoCollectionId") UUID photoCollectionId) throws Exception {
		String uri = service.execute(file, token, photoCollectionId, uriBuilder);
		return ResponseEntity.created(new URI(uri)).build();
	}

}
