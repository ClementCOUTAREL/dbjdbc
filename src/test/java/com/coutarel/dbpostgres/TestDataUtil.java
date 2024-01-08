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

  public static Book createTestBookA(){
    return Book.builder()
      .isbn("sqfsdfc")
      .title("Au nom de la Rose")
      .authorId(1L)
      .build();
  }

  public static Book createTestBookB(){
    return Book.builder()
      .isbn("abc")
      .title("Harry Potter")
      .authorId(1L)
      .build();
  }

  public static Book createTestBookC(){
    return Book.builder()
      .isbn("cba")
      .title("Universe")
      .authorId(1L)
      .build();
  }

}
