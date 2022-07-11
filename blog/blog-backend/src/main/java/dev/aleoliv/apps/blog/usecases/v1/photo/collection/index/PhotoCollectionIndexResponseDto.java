package dev.aleoliv.apps.blog.usecases.v1.photo.collection.index;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class PhotoCollectionIndexResponseDto {

	private UUID id;
	private String title;
	private UUID userId;
	private String username;
	private List<String> photoUrls;
	public Boolean isLoggedUser;
}
