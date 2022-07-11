package dev.aleoliv.thezueranetwork.thezueraneverendsapp.usecases.v1.phrases.create.services;

import org.springframework.stereotype.Service;

import dev.aleoliv.thezueranetwork.thezueraneverendsapp.shared.database.entities.PhraseEntity;
import dev.aleoliv.thezueranetwork.thezueraneverendsapp.shared.database.repositories.UserRepository;
import dev.aleoliv.thezueranetwork.thezueraneverendsapp.shared.exceptions.ServiceApiException;
import dev.aleoliv.thezueranetwork.thezueraneverendsapp.usecases.v1.phrases.create.dtos.CreatePhraseRequestDTO;
import dev.aleoliv.thezueranetwork.thezueraneverendsapp.usecases.v1.phrases.create.dtos.CreatePhraseResponseDTO;
import dev.aleoliv.thezueranetwork.thezueraneverendsapp.usecases.v1.phrases.create.repositories.CreatePhraseRepository;

@Service
public class CreatePhraseService {

  private final CreatePhraseRepository createPhraseRepository;
  private final UserRepository userRepository;

  public CreatePhraseService(CreatePhraseRepository createPhraseRepository, UserRepository userRepository) {
    this.createPhraseRepository = createPhraseRepository;
    this.userRepository = userRepository;
  }

  public CreatePhraseResponseDTO execute(CreatePhraseRequestDTO requestDTO) {

    var optionalUser = userRepository.findById(requestDTO.getUserId());

    if (!optionalUser.isPresent()) {
      throw new ServiceApiException("User don't exists.");
    }

    var optionalPhrase = createPhraseRepository.findByTextAndAuthor(requestDTO.getText(), optionalUser.get());

    if (optionalPhrase.isPresent()) {
      throw new ServiceApiException("Phrase exists!");
    }

    var entity = new PhraseEntity(requestDTO.getText(), optionalUser.get());

    var phraseEntity = createPhraseRepository.saveAndFlush(entity);

    return new CreatePhraseResponseDTO(phraseEntity.getText(), phraseEntity.getAuthor().getName());
  }
}
