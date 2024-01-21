package com.coutarel.dbpostgres.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.coutarel.dbpostgres.domain.entities.BookEntity;

public interface BookService {

  BookEntity createUpdatedBook(String isbn, BookEntity bookEntity);

  List<BookEntity> findAll();

  Page<BookEntity> findAll(Pageable pageable);

  Optional<BookEntity> findByIsbn(String isbn);

  boolean isExists(String isbn);

  BookEntity partialUpdate(String isbn, BookEntity bookEntity);

  void deleteByIsbn(String isbn);

}
