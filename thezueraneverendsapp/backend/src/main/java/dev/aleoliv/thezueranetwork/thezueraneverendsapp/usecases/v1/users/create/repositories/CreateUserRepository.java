package dev.aleoliv.thezueranetwork.thezueraneverendsapp.usecases.v1.users.create.repositories;

import java.util.Optional;

import dev.aleoliv.thezueranetwork.thezueraneverendsapp.shared.database.entities.UserEntity;
import dev.aleoliv.thezueranetwork.thezueraneverendsapp.shared.database.repositories.UserRepository;

public interface CreateUserRepository extends UserRepository {

  Optional<UserEntity> findByEmail(String email);

}
