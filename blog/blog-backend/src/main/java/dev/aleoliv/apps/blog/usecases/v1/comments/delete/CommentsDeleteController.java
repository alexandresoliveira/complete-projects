package dev.aleoliv.apps.blog.usecases.v1.comments.delete;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/posts/{postId}/comments")
public class CommentsDeleteController {

	private final CommentsDeleteService commentsDeleteService;

	public CommentsDeleteController(CommentsDeleteService commentsDeleteService) {
		this.commentsDeleteService = commentsDeleteService;
	}

	@DeleteMapping(value = { "{commentId}" })
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<?> handle(@PathVariable("commentId") UUID commentId, @PathVariable("postId") UUID postId,
			@RequestHeader("Authorization") String token) throws Exception {
		commentsDeleteService.execute(commentId, postId, token);
		return ResponseEntity.noContent().build();
	}
}
