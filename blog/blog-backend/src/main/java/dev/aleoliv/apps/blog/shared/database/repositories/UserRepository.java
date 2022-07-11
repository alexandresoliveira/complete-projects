package dev.aleoliv.apps.blog.shared.database.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.aleoliv.apps.blog.shared.database.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
	
	Optional<UserEntity> findByEmail(String email);
}
