package dev.aleoliv.apps.blog.usecases.v1.posts.index;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CommentResponseDto {

	private UUID id;
	private String description;
	private LocalDateTime createdAt;
	private UUID userId;
	private String username;
	private Boolean isLoggedUser = false;
}
