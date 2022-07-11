package dev.aleoliv.apps.blog.usecases.v1.comments.create;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.aleoliv.apps.blog.shared.configurations.security.TokenService;
import dev.aleoliv.apps.blog.shared.database.entities.CommentEntity;
import dev.aleoliv.apps.blog.shared.database.entities.PostEntity;
import dev.aleoliv.apps.blog.shared.database.entities.UserEntity;
import dev.aleoliv.apps.blog.shared.database.repositories.CommentRepository;

@Service
public class CommentsCreateService {

	private final CommentRepository commentRepository;
	private final TokenService tokenService;

	public CommentsCreateService(CommentRepository commentRepository, TokenService tokenService) {
		this.commentRepository = commentRepository;
		this.tokenService = tokenService;
	}

	@Transactional(rollbackFor = Exception.class)
	public CommentsCreateResponseDto execute(CommentsCreateRequestDto request, UUID postId, String token)
			throws Exception {
		UUID userId = tokenService.recoverUserId(token);

		CommentEntity commentEntity = new CommentEntity();
		commentEntity.setDescription(request.getDescription());
		commentEntity.setUser(new UserEntity(userId));
		commentEntity.setPost(new PostEntity(postId));

		CommentEntity entity = commentRepository.saveAndFlush(commentEntity);

		return new CommentsCreateResponseDto(entity.getId(), entity.getDescription(), entity.getCreatedAt(), userId,
				entity.getUser().getName(), true);
	}

}
