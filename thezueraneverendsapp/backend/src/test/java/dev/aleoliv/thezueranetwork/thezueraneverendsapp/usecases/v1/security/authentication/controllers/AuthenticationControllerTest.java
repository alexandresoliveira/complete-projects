package dev.aleoliv.thezueranetwork.thezueraneverendsapp.usecases.v1.security.authentication.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import dev.aleoliv.thezueranetwork.thezueraneverendsapp.usecases.v1.security.authentication.dtos.AuthenticationRequestDTO;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerTest {

  @Autowired
  MockMvc mokMvc;

  @Autowired
  ObjectMapper objectMapper;

  @Test
  public void shouldSendLoginInfoAndReturnOk() throws Exception {
    var requestDto = new AuthenticationRequestDTO("um@email.com", "123456");

    this.mokMvc.perform(MockMvcRequestBuilders.post("/authentication").contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(objectMapper.writeValueAsString(requestDto))).andExpect(MockMvcResultMatchers.status().isOk());
  }
}
