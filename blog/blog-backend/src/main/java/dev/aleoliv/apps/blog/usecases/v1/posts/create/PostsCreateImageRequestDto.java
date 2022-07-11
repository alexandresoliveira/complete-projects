package dev.aleoliv.apps.blog.usecases.v1.posts.create;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostsCreateImageRequestDto {

	MultipartFile file;
}
