package dev.aleoliv.apps.blog.usecases.v1.photo.collection.create;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PhotoCollectionCreateRequestDto {

	@NotNull
	@NotEmpty
	@Size(min = 3, max = 30)
	private String title;
}
