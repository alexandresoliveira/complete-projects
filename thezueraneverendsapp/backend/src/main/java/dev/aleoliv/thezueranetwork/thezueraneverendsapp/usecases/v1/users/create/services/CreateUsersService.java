package dev.aleoliv.thezueranetwork.thezueraneverendsapp.usecases.v1.users.create.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import dev.aleoliv.thezueranetwork.thezueraneverendsapp.shared.database.entities.UserEntity;
import dev.aleoliv.thezueranetwork.thezueraneverendsapp.shared.exceptions.ServiceApiException;
import dev.aleoliv.thezueranetwork.thezueraneverendsapp.usecases.v1.users.create.dtos.CreateUsersRequestDTO;
import dev.aleoliv.thezueranetwork.thezueraneverendsapp.usecases.v1.users.create.dtos.CreateUsersResponseDTO;
import dev.aleoliv.thezueranetwork.thezueraneverendsapp.usecases.v1.users.create.repositories.CreateUserRepository;

@Service
public class CreateUsersService {

  private final CreateUserRepository createUserRepository;

  public CreateUsersService(CreateUserRepository createUserRepository) {
    this.createUserRepository = createUserRepository;
  }

  public CreateUsersResponseDTO execute(CreateUsersRequestDTO requestDTO) {
    var optionalUserEntity = createUserRepository.findByEmail(requestDTO.getEmail());

    if (optionalUserEntity.isPresent()) {
      throw new ServiceApiException(
          String.format("Exists user with this email (%s)", optionalUserEntity.get().getEmail()));
    }

    var entity = new UserEntity();
    entity.setName(requestDTO.getName());
    entity.setEmail(requestDTO.getEmail());
    entity.setPassword(requestDTO.getPassword());

    var savedEntity = createUserRepository.saveAndFlush(entity);

    var responseDTO = new CreateUsersResponseDTO(savedEntity.getId(), savedEntity.getName(), savedEntity.getEmail());

    return responseDTO;
  }
}
