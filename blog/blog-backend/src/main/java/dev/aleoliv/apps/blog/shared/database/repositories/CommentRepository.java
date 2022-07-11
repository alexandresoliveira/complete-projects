package dev.aleoliv.apps.blog.shared.database.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import dev.aleoliv.apps.blog.shared.database.entities.CommentEntity;
import dev.aleoliv.apps.blog.shared.database.entities.PostEntity;
import dev.aleoliv.apps.blog.shared.database.entities.UserEntity;

public interface CommentRepository extends JpaRepository<CommentEntity, UUID> {

	Page<CommentEntity> findByPost(PostEntity post, Pageable pageable);
	
	Optional<CommentEntity> findByIdAndPostAndUser(UUID id, PostEntity post, UserEntity user);
	
	void deleteAllByPost(PostEntity post);
}
