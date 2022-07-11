package dev.aleoliv.apps.blog.usecases.v1.photo.collection.delete;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import dev.aleoliv.apps.blog.shared.configurations.security.TokenService;
import dev.aleoliv.apps.blog.shared.database.entities.PhotoCollectionEntity;
import dev.aleoliv.apps.blog.shared.database.entities.UserEntity;
import dev.aleoliv.apps.blog.shared.database.repositories.PhotoCollectionRepository;

@Service
public class PhotoCollectionDeleteService {

	private final PhotoCollectionRepository repository;
	private final TokenService tokenService;

	public PhotoCollectionDeleteService(PhotoCollectionRepository repository, TokenService tokenService) {
		this.repository = repository;
		this.tokenService = tokenService;
	}

	public void execute(UUID photoCollectionId, String token) throws Exception {
		UUID userId = tokenService.recoverUserId(token);

		Optional<PhotoCollectionEntity> optional = repository.findByIdAndUser(photoCollectionId,
				new UserEntity(userId));

		if (!optional.isPresent()) {
			throw new RuntimeException("Photo collection not found!");
		}

		repository.delete(optional.get());
	}
}
