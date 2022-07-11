package dev.aleoliv.apps.blog.usecases.v1.posts.index;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class PostsIndexResponseDto {

	private UUID id;
	private String text;
	private String link;
	private String linkPhoto;
	private UUID byUserId;
	private String byUsername;
	private Long totalComments;
	private Boolean isLoggedUser;
	private List<CommentResponseDto> comments;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
