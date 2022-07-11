package dev.aleoliv.apps.blog.usecases.v1.comments.delete;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import dev.aleoliv.apps.blog.shared.configurations.security.TokenService;
import dev.aleoliv.apps.blog.shared.database.entities.CommentEntity;
import dev.aleoliv.apps.blog.shared.database.entities.PostEntity;
import dev.aleoliv.apps.blog.shared.database.entities.UserEntity;
import dev.aleoliv.apps.blog.shared.database.repositories.CommentRepository;

@Service
public class CommentsDeleteService {

	private final CommentRepository commentRepository;
	private final TokenService tokenService;

	public CommentsDeleteService(CommentRepository commentRepository, TokenService tokenService) {
		this.commentRepository = commentRepository;
		this.tokenService = tokenService;
	}

	public void execute(UUID commentId, UUID postId, String token) throws Exception {
		UUID userId = tokenService.recoverUserId(token);

		Optional<CommentEntity> optional = commentRepository.findByIdAndPostAndUser(commentId, new PostEntity(postId),
				new UserEntity(userId));

		if (!optional.isPresent()) {
			throw new RuntimeException("Comment not found!");
		}

		commentRepository.delete(optional.get());
	}
}
