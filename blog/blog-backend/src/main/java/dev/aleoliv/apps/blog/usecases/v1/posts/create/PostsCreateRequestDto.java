package dev.aleoliv.apps.blog.usecases.v1.posts.create;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostsCreateRequestDto {

	@NotNull
	@NotEmpty
	@Size(min = 3, max = 256)
	private String text;
	
	private String link;
}
