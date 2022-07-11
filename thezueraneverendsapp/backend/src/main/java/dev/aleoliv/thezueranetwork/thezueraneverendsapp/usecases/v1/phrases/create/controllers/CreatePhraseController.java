package dev.aleoliv.thezueranetwork.thezueraneverendsapp.usecases.v1.phrases.create.controllers;

import dev.aleoliv.thezueranetwork.thezueraneverendsapp.usecases.v1.phrases.create.dtos.CreatePhraseRequestDTO;
import dev.aleoliv.thezueranetwork.thezueraneverendsapp.usecases.v1.phrases.create.dtos.CreatePhraseResponseDTO;
import dev.aleoliv.thezueranetwork.thezueraneverendsapp.usecases.v1.phrases.create.services.CreatePhraseService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "v1/phrases/create")
public class CreatePhraseController {

    private final CreatePhraseService service;

    public CreatePhraseController(CreatePhraseService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public CreatePhraseResponseDTO store(@RequestBody CreatePhraseRequestDTO dto) {
        return service.execute(dto);
    }
}
