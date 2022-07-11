package dev.aleoliv.apps.blog.usecases.v1.photo.collection.create;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.aleoliv.apps.blog.shared.configurations.security.TokenService;
import dev.aleoliv.apps.blog.shared.database.entities.PhotoCollectionEntity;
import dev.aleoliv.apps.blog.shared.database.entities.UserEntity;
import dev.aleoliv.apps.blog.shared.database.repositories.PhotoCollectionRepository;

@Service
public class PhotoCollectionCreateService {
	
	@Value("${blog.photo-collection.root-dir}")
	private String photoCollectionRootDir;

	private final PhotoCollectionRepository repository;
	private final TokenService tokenService;

	public PhotoCollectionCreateService(PhotoCollectionRepository repository, TokenService tokenService) {
		this.repository = repository;
		this.tokenService = tokenService;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public PhotoCollectionCreateResponseDto execute(PhotoCollectionCreateRequestDto request, String token, String prefixUrl) throws Exception {
		UUID userId = tokenService.recoverUserId(token);
		
		var entity = new PhotoCollectionEntity();
		entity.setTitle(request.getTitle());
		entity.setUser(new UserEntity(userId));
		
		PhotoCollectionEntity saveEntity = repository.saveAndFlush(entity);
		
		return PhotoCollectionCreateResponseDto
				.builder()
				.id(saveEntity.getId())
				.title(saveEntity.getTitle())
				.urlPhotos(String.format("%s/v1/photo_collections/%s/%s", prefixUrl, saveEntity.getUser().getId(), saveEntity.getTitle()))
				.userId(userId)
				.username(saveEntity.getUser().getName())
				.rootDirPhotoCollection(buildFolder(saveEntity.getId(), userId))
				.build();
	}

	private String buildFolder(UUID photoCollectionId, UUID userId) throws IOException {
		File file = new File(String.format("%s/%s/%s", photoCollectionRootDir, userId.toString(), photoCollectionId.toString()));
		if (!file.exists()) {
			file.mkdirs();
		}
		return file.getAbsolutePath();
	}

}
