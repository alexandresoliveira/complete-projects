package dev.aleoliv.apps.blog.usecases.v1.users.create;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("v1/users/create")
public class UsersCreateController {

	private final UsersCreateService service;

	public UsersCreateController(UsersCreateService service) {
		this.service = service;
	}

	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<UsersCreateResponseDto> handle(@RequestBody @Valid UsersCreateRequestDto requestDto,
			UriComponentsBuilder uriBuilder) throws URISyntaxException, IOException {
		var responseDto = service.execute(requestDto);
		URI uri = uriBuilder.path("/user/info/{id}").buildAndExpand(responseDto.getId()).toUri();
		return ResponseEntity.created(uri).body(responseDto);
	}
}
