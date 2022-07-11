package dev.aleoliv.thezueranetwork.thezueraneverendsapp.usecases.v1.users.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import dev.aleoliv.thezueranetwork.thezueraneverendsapp.shared.database.entities.UserEntity;
import dev.aleoliv.thezueranetwork.thezueraneverendsapp.usecases.v1.users.create.dtos.CreateUsersRequestDTO;
import dev.aleoliv.thezueranetwork.thezueraneverendsapp.usecases.v1.users.create.repositories.CreateUserRepository;
import dev.aleoliv.thezueranetwork.thezueraneverendsapp.usecases.v1.users.create.services.CreateUsersService;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CreateUsersServiceTest {

  @Mock
  private CreateUserRepository createUserRepository;

  @InjectMocks
  private CreateUsersService createUsersService;

  @Test
  @Order(1)
  public void shouldCreateUser() {
    var idUser = UUID.randomUUID();
    var nameUser = "name";
    var emailUser = "user@email.com";
    var passwordUser = "password";

    var requestDto = new CreateUsersRequestDTO("name", "user@email.com", "password");

    var entity = new UserEntity();
    // entity.setId(idUser);
    entity.setEmail(emailUser);
    entity.setName(nameUser);
    entity.setPassword(passwordUser);

    Mockito.when(createUserRepository.findByEmail(any(String.class))).thenReturn(Optional.empty());
    Mockito.when(createUserRepository.saveAndFlush(entity)).thenReturn(entity);

    var responseDto = createUsersService.execute(requestDto);

    assertThat(responseDto).isNotNull();
    Mockito.verify(createUserRepository).count();
  }

}
