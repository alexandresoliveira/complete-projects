package dev.aleoliv.apps.blog.usecases.v1.photo.collection.photo.store;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import dev.aleoliv.apps.blog.shared.components.storage.BlogStorage;
import dev.aleoliv.apps.blog.shared.configurations.security.TokenService;
import dev.aleoliv.apps.blog.shared.database.entities.PhotoCollectionEntity;
import dev.aleoliv.apps.blog.shared.database.entities.UserEntity;
import dev.aleoliv.apps.blog.shared.database.repositories.PhotoCollectionRepository;

@Service
public class PhotoCollectionPhotoStoreService {

	private final PhotoCollectionRepository repository;
	private final TokenService tokenService;
	private final BlogStorage blogStorage;

	public PhotoCollectionPhotoStoreService(PhotoCollectionRepository repository, TokenService tokenService,
			BlogStorage blogStorage) {
		this.repository = repository;
		this.tokenService = tokenService;
		this.blogStorage = blogStorage;
	}

	public String execute(MultipartFile file, String token, UUID photoCollectionId, UriComponentsBuilder uriBuilder)
			throws Exception {
		UUID userId = tokenService.recoverUserId(token);

		Optional<PhotoCollectionEntity> optional = repository.findByIdAndUser(photoCollectionId,
				new UserEntity(userId));

		if (!optional.isPresent()) {
			throw new Exception("Collection not found!");
		}

		String imageFolder = saveImage(userId, optional.get().getId(), file);

		UriComponents uriComponents = uriBuilder.build();

		return blogStorage.link(imageFolder, uriComponents.getHost(), uriComponents.getPort());
	}

	private String saveImage(UUID userId, UUID idPhotoCollection, MultipartFile multipartFile)
			throws IOException, URISyntaxException {
		String pathFolderFile = String.format("%s/%s", userId.toString(), idPhotoCollection.toString());

		String originalFilename = multipartFile.getOriginalFilename();

		return blogStorage.save(pathFolderFile, originalFilename, multipartFile);
	}
}
