package com.coutarel.dbpostgres.services;

import com.coutarel.dbpostgres.domain.entities.BookEntity;

public interface BookService {

  BookEntity createBook(String isbn, BookEntity bookEntity);

}
