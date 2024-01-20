package com.coutarel.dbpostgres.services;

import java.util.List;

import com.coutarel.dbpostgres.domain.entities.BookEntity;

public interface BookService {

  BookEntity createBook(String isbn, BookEntity bookEntity);

  List<BookEntity> findAll();

}
