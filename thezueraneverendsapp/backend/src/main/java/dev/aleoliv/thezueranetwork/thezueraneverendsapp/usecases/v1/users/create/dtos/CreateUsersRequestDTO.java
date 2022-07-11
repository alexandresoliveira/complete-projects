package dev.aleoliv.thezueranetwork.thezueraneverendsapp.usecases.v1.users.create.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateUsersRequestDTO {

    private final String name;
    private final String email;
    private final String password;
}
