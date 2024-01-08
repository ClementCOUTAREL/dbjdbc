package com.coutarel.dbpostgres;

import com.coutarel.dbpostgres.domain.Author;
import com.coutarel.dbpostgres.domain.Book;

public class TestDataUtil {

  public static Author createTestAuthorA(){
    return Author.builder()
      .id(1L)
      .name("Abigail Rose")
      .age(80)
      .build();
  }

  public static Author createTestAuthorB(){
    return Author.builder()
      .id(2L)
      .name("JK Rowling")
      .age(50)
      .build();
  }

  public static Author createTestAuthorC(){
    return Author.builder()
      .id(3L)
      .name("Werner")
      .age(60)
      .build();
  }

  public static Book createTestBookA(final Author author){
    return Book.builder()
      .isbn("sqfsdfc")
      .title("Au nom de la Rose")
      .author(author)
      .build();
  }

  public static Book createTestBookB(final Author author){
    return Book.builder()
      .isbn("abc")
      .title("Harry Potter")
      .author(author)
      .build();
  }

  public static Book createTestBookC(final Author author){
    return Book.builder()
      .isbn("cba")
      .title("Universe")
      .author(author)
      .build();
  }

}
