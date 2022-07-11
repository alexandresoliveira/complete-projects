package dev.aleoliv.thezueranetwork.thezueraneverendsapp.shared.database.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.aleoliv.thezueranetwork.thezueraneverendsapp.shared.database.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
}
