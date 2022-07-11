package dev.aleoliv.apps.blog.usecases.v1.posts.create;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PostsCreateResponseDto {

	private UUID id;
}
