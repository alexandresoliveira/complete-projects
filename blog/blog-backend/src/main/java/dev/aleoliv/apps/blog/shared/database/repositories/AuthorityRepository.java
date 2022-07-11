package dev.aleoliv.apps.blog.shared.database.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.aleoliv.apps.blog.shared.database.entities.AuthorityEntity;

public interface AuthorityRepository extends JpaRepository<AuthorityEntity, UUID> {

	Optional<AuthorityEntity> findByName(String name);

}
