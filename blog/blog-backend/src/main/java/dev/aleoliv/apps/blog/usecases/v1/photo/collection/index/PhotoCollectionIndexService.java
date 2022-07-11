package dev.aleoliv.apps.blog.usecases.v1.photo.collection.index;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import dev.aleoliv.apps.blog.shared.components.storage.BlogStorage;
import dev.aleoliv.apps.blog.shared.configurations.security.TokenService;
import dev.aleoliv.apps.blog.shared.database.entities.PhotoCollectionEntity;
import dev.aleoliv.apps.blog.shared.database.entities.UserEntity;
import dev.aleoliv.apps.blog.shared.database.repositories.PhotoCollectionRepository;

@Service
public class PhotoCollectionIndexService {

	private final PhotoCollectionRepository repository;
	private final TokenService tokenService;
	private final ResourceLoader resourceLoader;
	private final BlogStorage blogStorage;

	public PhotoCollectionIndexService(PhotoCollectionRepository repository, TokenService tokenService, ResourceLoader resourceLoader, BlogStorage blogStorage) {
		this.repository = repository;
		this.tokenService = tokenService;
		this.resourceLoader = resourceLoader;
		this.blogStorage = blogStorage;
	}

	public Page<PhotoCollectionIndexResponseDto> execute(Pageable pageable, PhotoCollectionIndexRequestDto request,
			UUID photoCollectionUserId, String token, UriComponentsBuilder uriBuilder) throws Exception {
		UUID userId = tokenService.recoverUserId(token);
		
		var exampleEntity = new PhotoCollectionEntity();
		exampleEntity.setUser(new UserEntity(photoCollectionUserId));
		
	    Example<PhotoCollectionEntity> example = Example.of(exampleEntity);
		
		Page<PhotoCollectionEntity> posts = repository.findAll(repository.params(request, example), pageable);
		
		return posts.map(entity -> {
			var response = PhotoCollectionIndexResponseDto
					.builder()
					.id(entity.getId())
					.title(entity.getTitle())
					.userId(entity.getUser().getId())
					.username(entity.getUser().getName())
					.photoUrls(getLinkFiles(entity.getId(), entity.getUser().getId(), uriBuilder))
					.isLoggedUser(entity.getUser().getId().equals(userId))
					.build();
			return response;
		});
	}
	
	public PhotoCollectionIndexResponseDto execute(UUID photoCollectionId, UUID photoCollectionUserId, UriComponentsBuilder uriBuilder) {
		Optional<PhotoCollectionEntity> optional = repository.findById(photoCollectionId);
		if (!optional.isPresent()) {
			throw new RuntimeException("Photo collection not found!");
		}
		var entity = optional.get();
		var response = PhotoCollectionIndexResponseDto
				.builder()
				.id(entity.getId())
				.title(entity.getTitle())
				.userId(entity.getUser().getId())
				.username(entity.getUser().getName())
				.photoUrls(getLinkFiles(entity.getId(), entity.getUser().getId(), uriBuilder))
				.isLoggedUser(entity.getUser().getId().equals(photoCollectionUserId))
				.build();
		return response;
	}

	private List<String> getLinkFiles(UUID photoCollectionId, UUID userId, UriComponentsBuilder uriBuilder) {
		try {
			Resource resource = resourceLoader.getResource(String.format("classpath:static/photo_collections/%s/%s", userId.toString(), photoCollectionId));
			String[] filenames = resource.getFile().list();
			UriComponents uriComponents = uriBuilder.build();
			List<String> allFiles = Arrays.stream(filenames).map(filename -> {
				String file = String.format("static/photo_collections/%s/%s/%s", userId.toString(), photoCollectionId.toString(), filename);
				return blogStorage.link(file, uriComponents.getHost(), uriComponents.getPort());
			}).collect(Collectors.toList());
			return Collections.unmodifiableList(allFiles);
		} catch (IOException e) {
		}
		return Collections.emptyList();
	}
	
}
