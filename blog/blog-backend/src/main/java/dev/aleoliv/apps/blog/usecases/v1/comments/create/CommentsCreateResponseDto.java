package dev.aleoliv.apps.blog.usecases.v1.comments.create;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CommentsCreateResponseDto {

	private UUID id;
	private String description;
	private LocalDateTime createdAt;
	private UUID userId;
	private String username;
	private Boolean isLoggedUser = false;
}
