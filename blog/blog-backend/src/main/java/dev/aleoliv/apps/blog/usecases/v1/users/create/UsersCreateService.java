package dev.aleoliv.apps.blog.usecases.v1.users.create;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.aleoliv.apps.blog.shared.database.entities.AuthorityEntity;
import dev.aleoliv.apps.blog.shared.database.entities.PhotoCollectionEntity;
import dev.aleoliv.apps.blog.shared.database.entities.UserEntity;
import dev.aleoliv.apps.blog.shared.database.repositories.AuthorityRepository;
import dev.aleoliv.apps.blog.shared.database.repositories.PhotoCollectionRepository;
import dev.aleoliv.apps.blog.shared.database.repositories.UserRepository;

@Service
public class UsersCreateService {
	
	@Value("${blog.photo-collection.root-dir}")
	private String photoCollectionRootDir;
	
	@Value("${blog.photo-collection.suffix-collection-post-user}")
	private String suffixCollectionPostUser;

	private final UserRepository userRepository;
	private final AuthorityRepository authorityRepository;
	private final PhotoCollectionRepository photoCollectionRepository;

	public UsersCreateService(UserRepository userRepository, AuthorityRepository authorityRepository, PhotoCollectionRepository photoCollectionRepository) {
		this.userRepository = userRepository;
		this.authorityRepository = authorityRepository;
		this.photoCollectionRepository = photoCollectionRepository;
	}

	@Transactional(rollbackFor = { Exception.class })
	public UsersCreateResponseDto execute(UsersCreateRequestDto requestDto) throws IOException {
		Optional<UserEntity> optional = userRepository.findByEmail(requestDto.getEmail());

		if (optional.isPresent())
			throw new RuntimeException(String.format("This user email (%s) exists!", requestDto.getEmail()));

		UserEntity entity = new UserEntity();
		entity.setEmail(requestDto.getEmail());
		entity.setName(requestDto.getName());
		entity.setPassword(requestDto.getPassword());

		Optional<AuthorityEntity> optionalAuthority = authorityRepository.findByName("ROLE_USER");
		if (!optionalAuthority.isPresent())
			throw new RuntimeException("Authority 'User' not found!");

		Set<AuthorityEntity> authorities = new HashSet<>();
		authorities.add(optionalAuthority.get());
		entity.setAuthorities(authorities);

		UserEntity userEntity = userRepository.saveAndFlush(entity);
		
		var photoCollectionEntity = new PhotoCollectionEntity(String.format("%s%s", userEntity.getName().split(" ")[0], suffixCollectionPostUser), userEntity);
		PhotoCollectionEntity actualPhotoCollection = photoCollectionRepository.saveAndFlush(photoCollectionEntity);
		
		buildFolder(actualPhotoCollection.getId(), userEntity.getId());
		
		return new UsersCreateResponseDto(
				userEntity.getId(), 
				userEntity.getName(), 
				userEntity.getEmail(), 
				authorities
					.stream()
					.map(AuthorityEntity::getAuthority)
					.collect(Collectors.toSet()));
	}

	private String buildFolder(UUID photoCollectionId, UUID userId) throws IOException {
		File file = new File(String.format("%s/%s/%s", photoCollectionRootDir, userId.toString(), photoCollectionId.toString()));
		if (!file.exists()) {
			file.mkdirs();
		}
		return file.getAbsolutePath();
	}

}
