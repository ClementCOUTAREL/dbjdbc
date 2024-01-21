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
import com.coutarel.dbpostgres.domain.dto.AuthorDto;
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
    authorService.save(author);

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

  @Test
  public void testThatGetAuthorByIdReturns200StatusWhenFound() throws Exception{
    AuthorEntity author = TestDataUtil.createTestAuthorA();
    authorService.save(author);

    mockMvc.perform(
      MockMvcRequestBuilders.get("/authors/1").contentType(MediaType.APPLICATION_JSON)
    ).andExpect(
      MockMvcResultMatchers.status().isOk()
    );
  }

  @Test
  public void testThatGetAuthorByIdReturns404StatusWhenNotFound() throws Exception {
    AuthorEntity author = TestDataUtil.createTestAuthorA();
    authorService.save(author);

    mockMvc.perform(
        MockMvcRequestBuilders.get("/authors/2").contentType(MediaType.APPLICATION_JSON)).andExpect(
            MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  public void testThatGetAuthorByIdReturnsCorrectAuthor() throws Exception {
    AuthorEntity author = TestDataUtil.createTestAuthorA();
    authorService.save(author);

    mockMvc.perform(
        MockMvcRequestBuilders.get("/authors/1").contentType(MediaType.APPLICATION_JSON))
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.id").value(author.getId())
        )
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.name").value(author.getName())
        )
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.age").value(author.getAge())
        );
  }

  @Test
  public void testThatFullUpdateAuthorReturnsStatus404WhenNoAuthorExists() throws Exception {
    AuthorDto author = TestDataUtil.createTestAuthorDtoA();
    String authorJson = objectMapper.writeValueAsString(author);

    mockMvc.perform(
        MockMvcRequestBuilders.put("/authors/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(authorJson))
        .andExpect(
            MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  public void testThatFullUpdateAuthorReturnsStatus200WhenUpdated() throws Exception{
    AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
    authorService.save(authorEntity);
    authorEntity.setAge(50);
    authorEntity.setName("Tolkien");
    
    String authorJson = objectMapper.writeValueAsString(authorEntity);

    mockMvc.perform(
        MockMvcRequestBuilders.put("/authors/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(authorJson)
      ).andExpect(   
        MockMvcResultMatchers.status().isOk()
    );
  }

  @Test
  public void testThatFullUpdateAuthorReturnsUpdatedExistingAuthor() throws Exception {
    AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
    authorService.save(authorEntity);
    authorEntity.setAge(50);
    authorEntity.setName("Tolkien");

    String authorJson = objectMapper.writeValueAsString(authorEntity);

    mockMvc.perform(
        MockMvcRequestBuilders.put("/authors/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(authorJson))
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.id").value(authorEntity.getId())
            )
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.name").value(authorEntity.getName())
        )
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.age").value(authorEntity.getAge())
        );
  }

  @Test
  public void testThatPartialUpdateReturnsStatus201IfAuthorExists() throws Exception{
    AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
    AuthorEntity savedAuthorEntity = authorService.save(authorEntity);

    AuthorDto authorDto = TestDataUtil.createTestAuthorDtoA();
    String authorDtoString = objectMapper.writeValueAsString(authorDto);

    mockMvc.perform(
      MockMvcRequestBuilders.patch("/authors/" + savedAuthorEntity.getId()).contentType(MediaType.APPLICATION_JSON).content(authorDtoString)
    )
    .andExpect(
      MockMvcResultMatchers.status().isOk()
    );
  }

  @Test
  public void testThatPartialUpdateReturnsStatus404IfAuthorDoesNotExist() throws Exception {
    AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();

    AuthorDto authorDto = TestDataUtil.createTestAuthorDtoA();
    String authorDtoString = objectMapper.writeValueAsString(authorDto);

    mockMvc.perform(
        MockMvcRequestBuilders.patch("/authors/" + authorEntity.getId()).contentType(MediaType.APPLICATION_JSON)
            .content(authorDtoString))
        .andExpect(
            MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  public void testThatPartialUpdateReturnsUpdatedAuthorifExists() throws Exception {
    AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
    AuthorEntity savedAuthorEntity = authorService.save(authorEntity);

    AuthorDto authorDto = TestDataUtil.createTestAuthorDtoA();
    String authorDtoString = objectMapper.writeValueAsString(authorDto);

    mockMvc.perform(
        MockMvcRequestBuilders.patch("/authors/" + savedAuthorEntity.getId()).contentType(MediaType.APPLICATION_JSON)
            .content(authorDtoString))
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.id").value(savedAuthorEntity.getId()))
        .andExpect(
          MockMvcResultMatchers.jsonPath("$.name").value(authorDto.getName())
        )
        .andExpect(
          MockMvcResultMatchers.jsonPath("$.age").value(authorDto.getAge())
        );
  }

  @Test
  public void testThatDeleteAuthorReturnsStatus204ForNonExistingAuthors() throws Exception {
        mockMvc.perform(
        MockMvcRequestBuilders.delete("/authors/2").contentType(MediaType.APPLICATION_JSON))
        .andExpect(
            MockMvcResultMatchers.status().isNoContent());
  }

  @Test
  public void testThatDeleteAuthorReturnsStatus200ForExistingAuthor() throws Exception{
    AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
    authorService.save(authorEntity);

    mockMvc.perform(
      MockMvcRequestBuilders.delete("/authors/" + authorEntity.getId()).contentType(MediaType.APPLICATION_JSON)
    ).andExpect(
      MockMvcResultMatchers.status().isNoContent()
    );
  }

}
