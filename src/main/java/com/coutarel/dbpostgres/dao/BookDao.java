package com.coutarel.dbpostgres.dao;

import java.util.List;
import java.util.Optional;

import com.coutarel.dbpostgres.domain.Book;

public interface BookDao {

  void create(Book book);

  Optional<Book> find(String isbn);

  List<Book> findMany();

  void update(String isbn, Book book);

  void delete(String isbn);
}
