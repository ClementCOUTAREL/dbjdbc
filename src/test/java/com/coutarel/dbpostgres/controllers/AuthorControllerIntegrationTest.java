package com.coutarel.dbpostgres.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.coutarel.dbpostgres.TestDataUtil;
import com.coutarel.dbpostgres.domain.entities.AuthorEntity;
import com.coutarel.dbpostgres.services.AuthorService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class AuthorControllerIntegrationTest {

  private MockMvc mockMvc;

  private ObjectMapper objectMapper;

  private AuthorService authorService;

  @Autowired
  public AuthorControllerIntegrationTest(MockMvc mockMvc, AuthorService authorService){
    this.mockMvc = mockMvc;
    this.objectMapper = new ObjectMapper();
    this.authorService = authorService;
  }

  @Test
  public void testThatCreateAuthorSuccessfullyReturnsHttp201Created() throws Exception {
    AuthorEntity author = TestDataUtil.createTestAuthorA();
    author.setId(null);
    String authorJson = objectMapper.writeValueAsString(author);

    mockMvc.perform(
      MockMvcRequestBuilders.post("/authors")
      .contentType(MediaType.APPLICATION_JSON)
      .content(authorJson)
    ).andExpect(
      MockMvcResultMatchers.status().isCreated()
    );
  }

  @Test
  public void testThatCreateAuthorSuccessfullyReturnsSavedAuthor() throws Exception {
    AuthorEntity author = TestDataUtil.createTestAuthorA();
    author.setId(null);
    String authorJson = objectMapper.writeValueAsString(author);

    mockMvc.perform(
        MockMvcRequestBuilders.post("/authors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(authorJson))
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.id").isNumber())
        .andExpect(
          MockMvcResultMatchers.jsonPath("$.name").value(author.getName()))
        .andExpect(
          MockMvcResultMatchers.jsonPath("$.age").value(author.getAge()));
  }

  @Test
  public void testThatListAuthorsReturnsHttpStatus200() throws Exception{
     mockMvc.perform(
      MockMvcRequestBuilders.get("/authors")
        .contentType(MediaType.APPLICATION_JSON)
    ).andExpect(
      MockMvcResultMatchers.status().isOk()
    );
  }

  @Test
  public void testThatListAuthorsReturnsListOfAuthors() throws Exception {
    AuthorEntity author = TestDataUtil.createTestAuthorA();
    authorService.createAuthor(author);

    mockMvc.perform(
        MockMvcRequestBuilders.get("/authors")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.[0].id").isNumber())
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.[0].name").value(author.getName()))
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.[0].age").value(author.getAge()));
  }

}
