package dev.aleoliv.apps.blog.usecases.v1.posts.create;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import dev.aleoliv.apps.blog.shared.components.storage.BlogStorage;
import dev.aleoliv.apps.blog.shared.configurations.security.TokenService;
import dev.aleoliv.apps.blog.shared.database.entities.PhotoCollectionEntity;
import dev.aleoliv.apps.blog.shared.database.entities.PostEntity;
import dev.aleoliv.apps.blog.shared.database.entities.UserEntity;
import dev.aleoliv.apps.blog.shared.database.repositories.PhotoCollectionRepository;
import dev.aleoliv.apps.blog.shared.database.repositories.PostRepository;
import dev.aleoliv.apps.blog.shared.database.repositories.UserRepository;

@Service
public class PostsCreateService {

	@Value("${blog.photo-collection.suffix-collection-post-user}")
	private String suffixCollectionPostUser;

	private final PostRepository repository;
	private final TokenService tokenService;
	private final PhotoCollectionRepository photoCollectionRepository;
	private final UserRepository userRepository;
	private final BlogStorage blogStorage;

	public PostsCreateService(PostRepository repository, TokenService tokenService,
			PhotoCollectionRepository photoCollectionRepository, UserRepository userRepository,
			BlogStorage blogStorage) {
		this.repository = repository;
		this.tokenService = tokenService;
		this.photoCollectionRepository = photoCollectionRepository;
		this.userRepository = userRepository;
		this.blogStorage = blogStorage;
	}

	@Transactional(rollbackFor = Exception.class)
	public PostsCreateResponseDto execute(MultipartFile file, String token) throws Exception {
		UUID userId = tokenService.recoverUserId(token);

		Optional<UserEntity> optionalUser = userRepository.findById(userId);
		if (!optionalUser.isPresent()) {
			throw new RuntimeException("User not found!");
		}

		UserEntity userEntity = optionalUser.get();

		PostEntity post = new PostEntity();
		post.setText(file.getOriginalFilename());
		post.setUser(userEntity);

		PostEntity postEntity = repository.saveAndFlush(post);

		postEntity.setLinkPhoto(buildPostImage(file, postEntity, userEntity));

		repository.saveAndFlush(postEntity);

		return buildResponseDto(postEntity);
	}

	@Transactional(rollbackFor = Exception.class)
	public PostsCreateResponseDto execute(PostsCreateRequestDto request, String token) throws Exception {
		UUID userId = tokenService.recoverUserId(token);

		Optional<UserEntity> optionalUser = userRepository.findById(userId);
		if (!optionalUser.isPresent()) {
			throw new RuntimeException("User not found!");
		}

		UserEntity userEntity = optionalUser.get();

		PostEntity post = new PostEntity();
		post.setText(request.getText());
		post.setUser(userEntity);

		PostEntity postEntity = repository.saveAndFlush(post);

		postEntity.setLink(buildPostLink(request.getLink(), userId));

		repository.saveAndFlush(postEntity);

		return buildResponseDto(postEntity);
	}

	private String buildPostLink(String link, UUID userId) {
		return link;
	}

	private String buildPostImage(MultipartFile file, PostEntity postEntity, UserEntity userEntity)
			throws IOException, URISyntaxException {
		if (file == null) {
			return "";
		}

		String postPhotoCollectionTitle = String.format("%s%s", userEntity.getName().split(" ")[0],
				suffixCollectionPostUser);
		Optional<PhotoCollectionEntity> optional = photoCollectionRepository
				.findByTitleAndUser(postPhotoCollectionTitle, userEntity);

		var photoCollection = optional.orElseGet(() -> {
			var photoCollectionPost = new PhotoCollectionEntity();
			photoCollectionPost.setTitle(postPhotoCollectionTitle);
			photoCollectionPost.setUser(userEntity);
			return photoCollectionRepository.saveAndFlush(photoCollectionPost);
		});

		return saveImage(userEntity.getId(), photoCollection.getId(), postEntity.getId(), file);
	}

	private PostsCreateResponseDto buildResponseDto(PostEntity postEntity) {
		return new PostsCreateResponseDto(postEntity.getId());
	}

	/**
	 * 
	 * Format path file:
	 * 
	 * {id_user}/{id_collection}/{id_post}.{ext_file}
	 * 
	 * @param id
	 * @param userId
	 * @param multipartFile
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	private String saveImage(UUID userId, UUID idPhotoCollection, UUID idPost, MultipartFile multipartFile)
			throws IOException, URISyntaxException {
		String pathFolderFile = String.format("%s/%s", userId.toString(), idPhotoCollection.toString());

		String originalFilename = multipartFile.getOriginalFilename();

		String filename = String.format("%s%s", idPost, originalFilename.substring(originalFilename.indexOf(".")));

		return blogStorage.save(pathFolderFile, filename, multipartFile);
	}
}
