package dev.aleoliv.thezueranetwork.thezueraneverendsapp.usecases.v1.phrases.create.repositories;

import java.util.Optional;

import dev.aleoliv.thezueranetwork.thezueraneverendsapp.shared.database.entities.PhraseEntity;
import dev.aleoliv.thezueranetwork.thezueraneverendsapp.shared.database.entities.UserEntity;
import dev.aleoliv.thezueranetwork.thezueraneverendsapp.shared.database.repositories.PhraseRepository;

public interface CreatePhraseRepository extends PhraseRepository {

  Optional<PhraseEntity> findByTextAndAuthor(String text, UserEntity author);

}
