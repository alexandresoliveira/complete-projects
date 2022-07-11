package dev.aleoliv.apps.blog.usecases.v1.photo.collection.create;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PhotoCollectionCreateResponseDto {

	private UUID id;
	private String title;
	private String urlPhotos;
	private UUID userId;
	private String username;
	private LocalDateTime createdAt;
	private String rootDirPhotoCollection;
}
