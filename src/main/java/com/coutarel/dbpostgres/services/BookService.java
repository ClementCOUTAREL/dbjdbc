package com.coutarel.dbpostgres.services;

import java.util.List;
import java.util.Optional;

import com.coutarel.dbpostgres.domain.entities.BookEntity;

public interface BookService {

  BookEntity createUpdatedBook(String isbn, BookEntity bookEntity);

  List<BookEntity> findAll();

  Optional<BookEntity> findByIsbn(String isbn);

  boolean isExists(String isbn);

}
