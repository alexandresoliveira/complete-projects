package dev.aleoliv.thezueranetwork.thezueraneverendsapp.usecases.v1.phrases.create.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreatePhraseResponseDTO {
    private final String text;
    private final String author;
}
