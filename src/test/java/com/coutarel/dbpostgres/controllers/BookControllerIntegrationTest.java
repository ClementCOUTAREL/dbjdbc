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
import com.coutarel.dbpostgres.domain.dto.BookDto;
import com.coutarel.dbpostgres.domain.entities.BookEntity;
import com.coutarel.dbpostgres.services.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext( classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookControllerIntegrationTest {

  private MockMvc mockMvc;

  private ObjectMapper objectMapper;

  private BookService bookService;

  @Autowired
  public BookControllerIntegrationTest(MockMvc mockMvc, BookService bookService){
    this.mockMvc = mockMvc;
    this.objectMapper = new ObjectMapper();
    this.bookService = bookService;
  }

  @Test
  public void testThatCreateBookSuccessfullyReturnsHttp201Created() throws Exception{
    BookDto book = TestDataUtil.createTestBookDtoA(null);
    String createBookJson = objectMapper.writeValueAsString(book);

    mockMvc.perform(
      MockMvcRequestBuilders.put("/books/" + book.getIsbn())
      .contentType(MediaType.APPLICATION_JSON)
      .content(createBookJson)
    ).andExpect(
      MockMvcResultMatchers.status().isCreated()
    );
  }

  @Test
  public void testThatCreateBookSuccessfullyReturnsCreatedBook() throws Exception {
    BookDto book = TestDataUtil.createTestBookDtoA(null);
    String createBookJson = objectMapper.writeValueAsString(book);

    mockMvc.perform(
        MockMvcRequestBuilders.put("/books/" + book.getIsbn())
            .contentType(MediaType.APPLICATION_JSON)
            .content(createBookJson))
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.isbn").value(book.getIsbn()))
            .andExpect(
              MockMvcResultMatchers.jsonPath("$.title").value(book.getTitle())
            )
            .andExpect(
              MockMvcResultMatchers.jsonPath("$.author").value(book.getAuthor())
            );
  }

  @Test
  public void testThatListBookSuccessfullyReturnsHttp200() throws Exception {
    BookEntity book = TestDataUtil.createTestBookA(null);
    String createBookJson = objectMapper.writeValueAsString(book);

    mockMvc.perform(
        MockMvcRequestBuilders.get("/books")
            .contentType(MediaType.APPLICATION_JSON)
            .content(createBookJson))
        .andExpect(
            MockMvcResultMatchers.status().isOk());
  }

  @Test
  public void testThatListBookSuccessfullyReturnsListOfBook() throws Exception {
    BookEntity book = TestDataUtil.createTestBookA(null);
    bookService.createBook("111", book);

    mockMvc.perform(
        MockMvcRequestBuilders.get("/books")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.[0].isbn").value(book.getIsbn()))
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.[0].title").value(book.getTitle()))
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.[0].author").value(book.getAuthor()));
  }

}
