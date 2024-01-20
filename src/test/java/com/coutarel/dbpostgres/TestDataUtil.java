package com.coutarel.dbpostgres;

import com.coutarel.dbpostgres.domain.dto.AuthorDto;
import com.coutarel.dbpostgres.domain.dto.BookDto;
import com.coutarel.dbpostgres.domain.entities.AuthorEntity;
import com.coutarel.dbpostgres.domain.entities.BookEntity;

public class TestDataUtil {

  public static AuthorEntity createTestAuthorA(){
    return AuthorEntity.builder()
      .id(1L)
      .name("Abigail Rose")
      .age(80)
      .build();
  }

  public static AuthorDto createTestAuthorDtoA() {
    return AuthorDto.builder()
        .id(1L)
        .name("Abigail Rose")
        .age(80)
        .build();
  }

  public static AuthorEntity createTestAuthorB(){
    return AuthorEntity.builder()
      .id(2L)
      .name("JK Rowling")
      .age(50)
      .build();
  }

  public static AuthorEntity createTestAuthorC(){
    return AuthorEntity.builder()
      .id(3L)
      .name("Werner")
      .age(60)
      .build();
  }

  public static BookEntity createTestBookA(final AuthorEntity author){
    return BookEntity.builder()
      .isbn("sqfsdfc")
      .title("Au nom de la Rose")
      .author(author)
      .build();
  }

  public static BookEntity createTestBookB(final AuthorEntity author){
    return BookEntity.builder()
      .isbn("abc")
      .title("Harry Potter")
      .author(author)
      .build();
  }

  public static BookEntity createTestBookC(final AuthorEntity author){
    return BookEntity.builder()
      .isbn("cba")
      .title("Universe")
      .author(author)
      .build();
  }

  public static BookDto createTestBookDtoA(final AuthorDto author) {
    return BookDto.builder()
        .isbn("sqfsdfc")
        .title("Au nom de la Rose")
        .author(author)
        .build();
  }

  public static BookDto createTestBookDtoB(final AuthorDto author) {
    return BookDto.builder()
        .isbn("abc")
        .title("Harry Potter")
        .author(author)
        .build();
  }

  public static BookDto createTestBookDtoC(final AuthorDto author) {
    return BookDto.builder()
        .isbn("cba")
        .title("Universe")
        .author(author)
        .build();
  }

}
