package dev.aleoliv.apps.blog.usecases.v1.posts.index;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class PostsIndexRequestDto {
	
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private LocalDateTime startCreatedAt;
	
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private LocalDateTime endCreatedAt;
	
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private LocalDateTime startUpdatedAt;
	
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private LocalDateTime endUpdatedAt;
}
