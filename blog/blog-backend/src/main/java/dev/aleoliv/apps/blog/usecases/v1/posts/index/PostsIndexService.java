package dev.aleoliv.apps.blog.usecases.v1.posts.index;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import dev.aleoliv.apps.blog.shared.components.storage.BlogStorage;
import dev.aleoliv.apps.blog.shared.configurations.security.TokenService;
import dev.aleoliv.apps.blog.shared.database.entities.CommentEntity;
import dev.aleoliv.apps.blog.shared.database.entities.PostEntity;
import dev.aleoliv.apps.blog.shared.database.repositories.CommentRepository;
import dev.aleoliv.apps.blog.shared.database.repositories.PostRepository;

@Service
public class PostsIndexService {

	private final PostRepository postRepository;
	private final CommentRepository commentRepository;
	private final TokenService tokenService;
	private final BlogStorage blogStorage;

	public PostsIndexService(PostRepository postRepository, CommentRepository commentRepository, TokenService tokenService, BlogStorage blogStorage) {
		this.postRepository = postRepository;
		this.commentRepository = commentRepository;
		this.tokenService = tokenService;
		this.blogStorage = blogStorage;
	}
	
	public Page<PostsIndexResponseDto> execute(Pageable pageable,
			PostsIndexRequestDto dto, String token, UriComponentsBuilder uriBuilder) throws Exception {
	    
		UUID userId = tokenService.recoverUserId(token);
		
		Page<PostEntity> posts = postRepository.findAll(postRepository.params(dto, null), pageable);
		
		UriComponents uriComponents = uriBuilder.build();
		
		return posts.map(post -> {
			Pageable commentPageable = PageRequest.of(0, 2, Sort.by(Direction.DESC, "createdAt"));
			Page<CommentEntity> comments = commentRepository.findByPost(post, commentPageable);
			
			List<CommentResponseDto> commentsDto = comments.getContent().stream().map(comment -> {
				boolean isLoggedUser = comment.getUser().getId().equals(userId);
				var commentResponseDto = new CommentResponseDto(
						comment.getId(), comment.getDescription(), comment.getCreatedAt(), comment.getUser().getId(), comment.getUser().getName(), isLoggedUser);
				return commentResponseDto;
			}).collect(Collectors.toList());
			
			boolean isPostLoggedUser = post.getUser().getId().equals(userId);
			
			return PostsIndexResponseDto.builder()
					.id(post.getId())
					.text(post.getText())
					.link(post.getLink())
					.linkPhoto(blogStorage.link(post.getLinkPhoto(), uriComponents.getHost(), uriComponents.getPort()))
					.byUserId(post.getUser().getId())
					.byUsername(post.getUser().getName())
					.createdAt(post.getCreatedAt())
					.updatedAt(post.getUpdatedAt())
					.isLoggedUser(isPostLoggedUser)
					.totalComments(comments.getTotalElements())
					.comments(commentsDto)
					.build();
		});
	}
	
}
