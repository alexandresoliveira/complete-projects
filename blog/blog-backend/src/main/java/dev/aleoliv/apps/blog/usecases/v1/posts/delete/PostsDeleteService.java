package dev.aleoliv.apps.blog.usecases.v1.posts.delete;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import dev.aleoliv.apps.blog.shared.components.storage.BlogStorage;
import dev.aleoliv.apps.blog.shared.configurations.security.TokenService;
import dev.aleoliv.apps.blog.shared.database.entities.PostEntity;
import dev.aleoliv.apps.blog.shared.database.repositories.PostRepository;

@Service
public class PostsDeleteService {

	private final PostRepository postRepository;
	private final TokenService tokenService;
	private final BlogStorage blogStorage;

	public PostsDeleteService(PostRepository postRepository, TokenService tokenService, BlogStorage blogStorage) {
		this.postRepository = postRepository;
		this.tokenService = tokenService;
		this.blogStorage = blogStorage;
	}

	@Transactional(rollbackFor = Exception.class)
	void execute(UUID id, String token) throws Exception {
		UUID userId = tokenService.recoverUserId(token);

		Optional<PostEntity> optional = postRepository.findByIdAndUserId(id, userId);

		if (!optional.isPresent())
			throw new RuntimeException("You don't have authorization to execute this action!");

		PostEntity postEntity = optional.get();
		
		postRepository.delete(postEntity);
		
		if (StringUtils.hasText(postEntity.getLinkPhoto()))
			blogStorage.delete(optional.get().getLinkPhoto());
	}

}
